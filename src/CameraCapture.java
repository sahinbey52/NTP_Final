import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

public class CameraCapture {
    private static boolean grilestir = false;

    public static void main(String[] args) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();

            Java2DFrameConverter converter = new Java2DFrameConverter();
            final BufferedImage[] image = {converter.getBufferedImage(grabber.grab(), 1)};

            JFrame frame = new JFrame("Kamera Görüntüsü");
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image[0], 0, 0, null);
                }
            };

            JButton grilestirButton = new JButton("Grileştir");
            grilestirButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    grilestir = !grilestir;
                }
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(image[0].getWidth(), image[0].getHeight());

            panel.add(grilestirButton);
            frame.getContentPane().add(panel);

            frame.setVisible(true);

            while (frame.isVisible()) {
                image[0] = converter.getBufferedImage(grabber.grab(), 1);

                if (grilestir) {
                    BufferedImage grayImage = new BufferedImage(image[0].getWidth(), image[0].getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                    ColorConvertOp op = new ColorConvertOp(null);
                    op.filter(image[0], grayImage);
                    image[0] = grayImage;
                }

                panel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                grabber.stop();
                grabber.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}