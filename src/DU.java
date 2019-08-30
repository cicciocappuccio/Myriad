import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DU {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Input missing!");
			return;
		}

		File inputDirectory = new File(args[0]);

		if (!inputDirectory.isAbsolute()) {
			System.out.println("Path '" + args[0] + "' don't is an absolute path; I try to read anyway...");
		}

		if (!inputDirectory.exists()) {
			System.out.println("Don't exists a file or directory with path '" + args[0]);
		} else if (!inputDirectory.isDirectory()) {
			System.out.println("Input string don't is a directory path!");
		} else {

			Map<File, Long> files = Arrays.stream(inputDirectory.listFiles())
					.collect(Collectors.toMap(x -> x, x -> sizeOf(x))).entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors
							.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

			files.forEach((x, y) -> print(x, y));
		}
	}

	private static long sizeOf(File file) {
		if (file.isFile()) {
			return file.length();
		} else {
			return Arrays.stream(file.listFiles()).map(elem -> sizeOf(elem)).reduce(0l, Long::sum);
		}
	}

	private static void print(File file, long size) {
		System.out.print((file.isDirectory() ? "DIR " : "FILE ") + file.getAbsolutePath() + " ");

		size = (long) Math.ceil(Double.valueOf(size) / 1024d);

		System.out.println(size + "KB");
	}
}
