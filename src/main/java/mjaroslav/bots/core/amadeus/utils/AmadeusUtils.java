package mjaroslav.bots.core.amadeus.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
import mjaroslav.bots.core.amadeus.AmadeusCore;

public class AmadeusUtils {
    public static final Comparator<String> LENGTH_SORTER = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.length() - o1.length();
        }
    };

    public static boolean existsOrCreateFolder(File folder) {
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }

    public static boolean existsOrCreateFile(File file) {
        try {
            return (file.exists() && file.isFile()) || file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existsOrCreateFolder(File folder, Action onCreate, Action onExists) {
        if (folder.exists() && folder.isDirectory()) {
            if (onExists != null)
                return onExists.done();
            return true;
        } else {
            if (folder.mkdirs()) {
                if (onCreate != null)
                    return onCreate.done();
                return true;
            }
        }
        return false;
    }

    public static boolean existsOrCreateFile(File file, Action onCreate, Action onExists) {
        if (file.exists() && file.isFile()) {
            if (onExists != null)
                return onExists.done();
            return true;
        } else {
            try {
                if (file.createNewFile()) {
                    if (onCreate != null)
                        return onCreate.done();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static FilenameFilter getFilenameExtFilter(String ext) {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return FilenameUtils.isExtension(name, ext);
            }
        };
    }

    public static boolean stringIsNotEmpty(String input) {
        return input != null && input.length() > 0;
    }

    public static boolean stringIsEmpty(String input) {
        return !stringIsNotEmpty(input);
    }

    /**
     * Always return string.
     *
     * @param input - String object.
     * @return Empty string if null.
     */
    public static String string(String input) {
        return stringIsEmpty(input) ? "" : input;
    }

    public static float[] toFloatArray(double[] input) {
        if (input != null) {
            float[] result = new float[input.length];
            for (int id = 0; id < input.length; id++)
                result[id] = (float) input[id];
            return result;
        }
        return new float[] {};
    }

    public static double[] toDoubleArray(float[] input) {
        if (input != null) {
            double[] result = new double[input.length];
            for (int id = 0; id < input.length; id++)
                result[id] = input[id];
            return result;
        }
        return new double[] {};
    }

    public static HashMap<String, String> parseArgsToMap(String args) throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        String argsString = args;
        int index = -1;
        while (argsString.length() > 0) {
            index = argsString.indexOf("=");
            String key = "", value = "";
            if (index != -1) {
                key = argsString.substring(0, index);
                value = "";
                argsString = argsString.substring(index + 1);
                index = -1;
                if (argsString.startsWith("\"")) {
                    argsString = argsString.substring(1);
                    index = argsString.indexOf("\"");
                    if (index != -1) {
                        value = argsString.substring(0, index);
                        argsString = argsString.substring(index + 1);
                    }
                } else {
                    index = argsString.indexOf(" ");
                    if (index != -1) {
                        value = argsString.substring(0, index);
                        argsString = argsString.substring(index);
                    } else {
                        value = argsString;
                        argsString = "";
                    }
                }
                argsString = argsString.trim();
                index = -1;
                result.put(key, value);
            } else
                throw new Exception("Error on args parsing >" + argsString);
        }
        return result;
    }

    public static ArrayList<String> parseArgsToArray(String args) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        String argsString = args;
        int index = 0;
        while (argsString.length() > 0) {
            if (index != -1) {
                if (argsString.startsWith("\"")) {
                    argsString = argsString.substring(1);
                    index = argsString.indexOf("\"");
                    if (index != -1) {
                        result.add(argsString.substring(0, index));
                        argsString = argsString.substring(index + 1);
                    }
                } else if (argsString.startsWith(" "))
                    argsString = argsString.substring(1);
                else {
                    index = argsString.indexOf(" ");
                    if (index != -1) {
                        result.add(argsString.substring(0, index));
                        argsString = argsString.substring(index + 1);
                    } else {
                        result.add(argsString);
                        argsString = "";
                    }
                }
            } else
                throw new Exception("Error on args parsing >" + argsString);
        }
        return result;
    }

    public static String removePreifx(String text, AmadeusCore core, Iterable<String> prefixes, boolean checkSpace) {
        if (text.startsWith("<@" + core.getClient().getOurUser().getLongID() + ">")
                || text.startsWith("<@!" + core.getClient().getOurUser().getLongID() + ">"))
            return text.substring(text.indexOf(">") + 1).trim();
        for (String prefix : prefixes) {
            if (text.toLowerCase().startsWith(prefix))
                if (!checkSpace || text.substring(prefix.length()).startsWith(" "))
                    return text.substring(prefix.length()).trim();
        }
        return text;
    }

    public static void waitAction(long checkTime, Action action) {
        long time = System.currentTimeMillis() + checkTime;
        while (!action.done() && System.currentTimeMillis() < time) {}
    }

    public static void waitAction(Action action) {
        while (!action.done()) {}
    }

    public static interface Action {
        public boolean done();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
