package Utils;

public class PathConverter {

    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    private static String convertToWindowsPath(String linuxPath) {
        if (linuxPath == null || linuxPath.isEmpty()) {
            return "";
        }

        // Replacing forward slashes with backslashes
        String windowsPath = linuxPath.replace('/', '\\');

        // If the path starts with a forward slash, remove it
        if (windowsPath.charAt(0) == '\\') {
            windowsPath = windowsPath.substring(1);
        }

        return windowsPath;
    }

    public static String convertPathBasedOnOS(String path) {
        if (isWindows()) {
            // If it's Windows, convert to Windows path
            return convertToWindowsPath(path);
        } else {
            // Otherwise, return the original path
            return path;
        }
    }
}
