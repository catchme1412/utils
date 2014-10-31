package com.raj.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class FileUtils {

    public static File[] getFiles(String path) {
        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };

        File dir = new File(path);

        return dir.listFiles(filter);
    }
    public static File[] getFilesMatching(String path, final String regExp) {
        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.matches(regExp);
            }
        };

        File dir = new File(path);

        return dir.listFiles(filter);
    }
    
    public static File[] getFilesEndingWith(String path, final String fileNameEndingWith) {
        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(fileNameEndingWith);
            }
        };

        File dir = new File(path);

        return dir.listFiles(filter);
    }

    public static FileInputStream getFileStream(String fileName) throws FileNotFoundException {
        return new FileInputStream(fileName);
    }

    public InputStream loadAsResource(String name) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader();
        }
        return loader.getResourceAsStream (name);
    }
    

    /**
     * Load a given resource. <p/> This method will try to load the resource
     * using the following methods (in order):
     * <ul>
     * <li>From Thread.currentThread().getContextClassLoader()
     * <li>From ClassLoaderUtil.class.getClassLoader()
     * <li>callingClass.getClassLoader()
     * </ul>
     * 
     * @param resourceName The name of the resource to load
     * @param callingClass The Class object of the calling object
     */
    public static URL getResource(String resourceName, Class callingClass) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null && resourceName.startsWith("/")) {
            //certain classloaders need it without the leading /
            url = Thread.currentThread().getContextClassLoader()
                .getResource(resourceName.substring(1));
        }

        ClassLoader cluClassloader = callingClass.getClassLoader();
        if (cluClassloader == null) {
            cluClassloader = ClassLoader.getSystemClassLoader();
        }
        if (url == null) {
            url = cluClassloader.getResource(resourceName);
        }
        if (url == null && resourceName.startsWith("/")) {
            //certain classloaders need it without the leading /
            url = cluClassloader.getResource(resourceName.substring(1));
        }

        if (url == null) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                url = cl.getResource(resourceName);
            }
        }

        if (url == null) {
            url = callingClass.getResource(resourceName);
        }
        
        if ((url == null) && (resourceName != null) && (resourceName.charAt(0) != '/')) {
            return getResource('/' + resourceName, callingClass);
        }

        return url;
    }
    
//    /**
//     * This is a convenience method to load a resource as a stream. <p/> The
//     * algorithm used to find the resource is given in getResource()
//     * 
//     * @param resourceName The name of the resource to load
//     * @param callingClass The Class object of the calling object
//     */
//    public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
//        URL url = getResource(resourceName, callingClass);
//
//        try {
//            return (url != null) ? url.openStream() : null;
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    /**
//     * Load a given resources. <p/> This method will try to load the resources
//     * using the following methods (in order):
//     * <ul>
//     * <li>From Thread.currentThread().getContextClassLoader()
//     * <li>From ClassLoaderUtil.class.getClassLoader()
//     * <li>callingClass.getClassLoader()
//     * </ul>
//     * 
//     * @param resourceName The name of the resource to load
//     * @param callingClass The Class object of the calling object
//     */
//    public static List<URL> getResources(String resourceName, Class callingClass) {
//        List<URL> ret = new ArrayList<URL>();
//        Enumeration<URL> urls = new Enumeration<URL>() {
//            public boolean hasMoreElements() {
//                return false;
//            }
//            public URL nextElement() {
//                return null;
//            }
//            
//        };
//        try {
//            urls = Thread.currentThread().getContextClassLoader()
//                .getResources(resourceName);
//        } catch (IOException e) {
//            //ignore
//        }
//        if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
//            //certain classloaders need it without the leading /
//            try {
//                urls = Thread.currentThread().getContextClassLoader()
//                    .getResources(resourceName.substring(1));
//            } catch (IOException e) {
//                // ignore
//            }
//        }
//
//        ClassLoader cluClassloader = Main.class.getClassLoader();
//        if (cluClassloader == null) {
//            cluClassloader = ClassLoader.getSystemClassLoader();
//        }
//        if (!urls.hasMoreElements()) {
//            try {
//                urls = cluClassloader.getResources(resourceName);
//            } catch (IOException e) {
//                // ignore
//            }
//        }
//        if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
//            //certain classloaders need it without the leading /
//            try {
//                urls = cluClassloader.getResources(resourceName.substring(1));
//            } catch (IOException e) {
//                // ignore
//            }
//        }
//
//        if (!urls.hasMoreElements()) {
//            ClassLoader cl = callingClass.getClassLoader();
//
//            if (cl != null) {
//                try {
//                    urls = cl.getResources(resourceName);
//                } catch (IOException e) {
//                    // ignore
//                }
//            }
//        }
//
//        if (!urls.hasMoreElements()) {
//            URL url = callingClass.getResource(resourceName);
//            if (url != null) {
//                ret.add(url);
//            }
//        }
//        while (urls.hasMoreElements()) {
//            ret.add(urls.nextElement());
//        }
//
//        
//        if (ret.isEmpty() && (resourceName != null) && (resourceName.charAt(0) != '/')) {
//            return getResources('/' + resourceName, callingClass);
//        }
//        return ret;
//    }




}
