import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NonBlockingSolution {

	private static Thread currentThread = Thread.currentThread();
	private static ByteBuffer buffer;

	public void run() {

		readFile("Input.txt");
	}

	private void readFile(String path) {
		try {

			AsynchronousFileChannel ifc = AsynchronousFileChannel.open(Path.of(path), StandardOpenOption.READ);

			buffer = ByteBuffer.allocate((int) ifc.size());
			long position = 0;

			ifc.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					System.out.println(attachment + " completed and " + result + " bytes are read. ");
					writeFile("Output.txt");
					currentThread.interrupt();
				}

				@Override
				public void failed(Throwable e, ByteBuffer attachment) {
					System.out.println(attachment + " failed with exception:");
					e.printStackTrace();
					currentThread.interrupt();
				}
			});

			try {

				currentThread.join();

			} catch (InterruptedException e) {
			}

			ifc.close();

		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

	private void writeFile(String path) {

		try {
			
			AsynchronousFileChannel ofc = AsynchronousFileChannel.open(Path.of(path), StandardOpenOption.CREATE, StandardOpenOption.WRITE );

			buffer.flip();
			
			ofc.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					System.out.println(attachment + " completed and " + result + " bytes are written. ");
					buffer.clear();
					currentThread.interrupt();
				}

				@Override
				public void failed(Throwable e, ByteBuffer attachment) {
					System.out.println(attachment + " failed with exception:");
					e.printStackTrace();
					currentThread.interrupt();
				}
			});
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			currentThread.join();

		} catch (InterruptedException e) {}

	}
}
