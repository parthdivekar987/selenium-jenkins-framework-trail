package com.onboarding.automation.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class EnvConfig {
    private static final Map<String, String> env = new HashMap<>();
    private static boolean loaded = false;

   public static void load() {
       if (loaded) return;
        
       // First, copy system env vars into map for easy lookup
       System.getenv().forEach(env::putIfAbsent);
       System.out.println("✅ EnvConfig: Loaded system environment variables");

       // Try to load .env from project root if present
       try {
           File f = new File(".env");
           if (f.exists() && f.isFile()) {
               System.out.println("📄 EnvConfig: Found .env file at " + f.getAbsolutePath());
               try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                   String line;
                   int loadedCount = 0;
                   while ((line = br.readLine()) != null) {
                       line = line.trim();
                       if (line.isEmpty() || line.startsWith("#")) continue;
                       int eq = line.indexOf('=');
                       if (eq <= 0) continue;
                       String k = line.substring(0, eq).trim();
                       String v = line.substring(eq + 1).trim();
                       // remove optional surrounding quotes
                       if ((v.startsWith("\"") && v.endsWith("\"")) || (v.startsWith("'") && v.endsWith("'"))) {
                           v = v.substring(1, v.length() - 1);
                       }
                       env.putIfAbsent(k, v);
                       loadedCount++;
                   }
                   System.out.println("✅ EnvConfig: Loaded " + loadedCount + " properties from .env");
               }
           } else {
               System.out.println("⚠️  EnvConfig: .env file not found at " + new File(".env").getAbsolutePath());
           }
       } catch (Exception e) {
           System.out.println("❌ EnvConfig: Error reading .env - " + e.getMessage());
           e.printStackTrace();
       }
       loaded = true;
   }

   public static String get(String key, String fallback) {
       if (!loaded) load();
       String value = env.getOrDefault(key, fallback);
       System.out.println("🔍 EnvConfig: get('" + key + "') = '" + value + "'");
       return value;
   }
}
