package org.firstinspires.ftc.teamcode.blaze;

import android.content.Context;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.sun.tools.javac.comp.Annotate;

import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Pure annotation scanner for the Blaze framework.
 * <p>
 * This class is solely responsible for reading/writing the cache file and
 * iterating through the APK's DexFile to find classes matching the rules
 * defined in {@link BlazeRegistry}. It does not hold any state itself.
 * </p>
 */
public class BlazeAnnotationScanner {
    private static final String tag = BlazeAnnotationScanner.class.getSimpleName();

    private static final String PACKAGE_NAME = "org.firstinspires.ftc.teamcode";
    private static final String CACHE_FILE_NAME = "blaze_modules.cache";

    private BlazeAnnotationScanner() {}

    /**
     * Scans the APK and populates the {@link BlazeRegistry}.
     *
     * @param hardwareMap The FTC HardwareMap, used to access the application context.
     */
    public static void scanAndRegisterAll(HardwareMap hardwareMap) {

        // 1. Reset registry state
        BlazeRegistry.resetRules();

        Context context = hardwareMap.appContext;
        File cacheFile = new File(context.getFilesDir(), CACHE_FILE_NAME);
        File apkFile = new File(context.getPackageCodePath());
        ClassLoader classLoader = BlazeModule.class.getClassLoader();



        boolean cacheHit = false;

        BlazeLogger.addDefaultLog(tag,"Started");

        long time = System.nanoTime();
        // 2. Check cache
        if (cacheFile.exists() && cacheFile.lastModified() >= apkFile.lastModified() && cacheFile.length() != 0)  {
            BlazeLogger.addDefaultLog(tag,"Found cache,reading");
            cacheHit = true;
            try (BufferedReader reader = new BufferedReader(new FileReader(cacheFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    BlazeRegistry.ScanRule matchedRule = null;
                    for (BlazeRegistry.ScanRule rule : BlazeRegistry.SCAN_RULES) {
                        if (line.startsWith(rule.getCachePrefix())) {
                            matchedRule = rule;
                            break;
                        }
                    }

                    if (matchedRule != null) {
                        String className = line.substring(matchedRule.getCachePrefix().length());
                        if (!className.isEmpty()) {
                            try {
                                Class<?> clazz = Class.forName(className, false, classLoader);
                                if (!matchedRule.onCacheFound(clazz, classLoader)) {
                                    cacheHit = false; break; // Invalidate cache
                                }
                            } catch (ClassNotFoundException | NoClassDefFoundError e) {
                                cacheHit = false; break; // Invalidate cache
                            }
                        }
                    } else {
                        cacheHit = false; break; // Unknown format
                    }
                }
            } catch (Exception e) {
                cacheHit = false;
            }
        }
        long timeToEnd = (System.nanoTime() - time) / 1000000;
        if(cacheHit){
            BlazeLogger.addDefaultLog(tag,"Caching finished in " + timeToEnd + "ms");
        }


        BlazeLogger.addDefaultLog(tag,"Past cache");

        time = System.nanoTime();
        long totalClassesScanned = 0;
        // 3. Full DexFile scan if cache missed
        if (!cacheHit) {
            dalvik.system.DexFile dexFile = null;
            BlazeLogger.addDefaultLog(tag,"No cache");
            try {
                dexFile = new dalvik.system.DexFile(context.getPackageCodePath());
                BlazeLogger.addDefaultLog(tag,"Dex:" + dexFile);
                Enumeration<String> entries = dexFile.entries();

                while (entries.hasMoreElements()) {
                    String className = entries.nextElement();
                    if (!className.startsWith(PACKAGE_NAME)) continue;

                    BlazeLogger.addDefaultLog(tag,"new entry:" + className);
                    totalClassesScanned++;
                    try {
                        Class<?> clazz = Class.forName(className, false, classLoader);
                        if (clazz.getAnnotations().length == 0) continue;
                        for (BlazeRegistry.ScanRule rule : BlazeRegistry.SCAN_RULES) {






                        BlazeLogger.addDefaultLog(tag,"target: " + rule.getTargetAnnotation()
                                + " existing " + clazz.getAnnotations()[0].getClass()
                                + " has :" + clazz.isAnnotationPresent(rule.getTargetAnnotation()));

                        BlazeLogger.addDefaultLog(tag,"targetLoader: " + rule.getClass().getClassLoader() +
                                " existing Loader: " + clazz.getClassLoader());
                        if (clazz.isAnnotationPresent(rule.getTargetAnnotation())) {
                            BlazeLogger.addDefaultLog(tag,"new entry:" + className + "Passed for r :" + rule.getCachePrefix());
                            if(rule.isValid(clazz)) {
                                rule.onDexFound(clazz);
                            }

                        }
                    }

                    } catch (ClassNotFoundException | NoClassDefFoundError throwable) {
                        BlazeLogger.addDefaultLog(tag,throwable.getMessage());
                    }
                }
            } catch (Exception e) {
                BlazeLogger.addDefaultLog(tag,"Failed" + e.getMessage()) ;
                throw new RuntimeException("Failed to scan APK: " + e.getMessage(), e);
            } finally {
                if (dexFile != null) {
                    timeToEnd = (System.nanoTime() - time) / 1000000;
                    BlazeLogger.addDefaultLog(tag,"Scan finished in " + timeToEnd + "ms");
                    BlazeLogger.addDefaultLog(tag,"Total files scanned: " + totalClassesScanned);
                    try { dexFile.close(); } catch (IOException ignored) {}
                }
            }

            // 4. Write new cache
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(cacheFile))) {
                for (BlazeRegistry.ScanRule rule : BlazeRegistry.SCAN_RULES) {
                    BlazeLogger.addDefaultLog(tag,"passing writing to " + rule);
                    rule.writeCache(writer);
                }
            } catch (IOException e) {
                BlazeLogger.addErrorLog("BlazeAnnotationScanner", "Cache write failed: " + e.getMessage());
            }
        }

        BlazeLogger.addDefaultLog(tag,"Past dex");


        // 5. Finalize registration in the Registry
        BlazeRegistry.finalizeModuleRegistration(classLoader);
    }


    /**
     * Deletes the cache file, forcing a full DexFile scan on the next run.
     */
    public static void removeCache(HardwareMap hardwareMap) {
        BlazeLogger.addDefaultLog("BlazeAnnotationScanner", "Deleting cache.");
        Context context = hardwareMap.appContext;
        File cacheFile = new File(context.getFilesDir(), CACHE_FILE_NAME);
        if (cacheFile.exists() && cacheFile.delete()) {
            BlazeLogger.addDefaultLog("BlazeAnnotationScanner", "Cache file successfully deleted.");
        }
    }
}