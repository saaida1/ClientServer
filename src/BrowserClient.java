import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BrowserClient extends JFrame {
    private JTextField searchBar;
    private JEditorPane contentPane;

    public BrowserClient() {
        setTitle("JSearch");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        searchBar = new JTextField();
        contentPane = new JEditorPane();
        contentPane.setContentType("text/html");

        // Create button for making requests
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeRequest();
            }
        });

        // Set layout
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchBar, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPane), BorderLayout.CENTER);
    }

    private void makeRequest() {
        try {
            String serverUrl = searchBar.getText();
            URL url = new URL(serverUrl);

            // Make a request to the server
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Display the response in the JEditorPane
            contentPane.setText(response.toString());
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BrowserClient browserClient = new BrowserClient();
                browserClient.setVisible(true);
            }
        });
    }
}
