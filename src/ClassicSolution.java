import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClassicSolution {

	public void run() {
		File input = new File("Input.txt");
		File output = new File("Output.txt");

		try {

			Scanner reader = new Scanner(input);
			FileWriter writer = new FileWriter(output);
			int counter = 0;

			while (reader.hasNextLine()) {

				String data = reader.nextLine();
				writer.write("[" + counter++ + "]: " + data + "\n\n");
			}

			reader.close();
			writer.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
