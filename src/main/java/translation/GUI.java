package translation;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.List;

// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            JSONTranslator json = new JSONTranslator();
            JComboBox countryBox = new JComboBox<>();
            List<String> countryCodes = json.getCountryCodes();
            for (int i = 0; i < countryCodes.size(); i++) {
                countryBox.addItem(countryCodeConverter.fromCountryCode(countryCodes.get(i)));
            }
            List<String> langCodes = json.getLanguageCodes();
            String[] langs = new String[langCodes.size()];
            for (int i = 0; i < langCodes.size(); i++) {
                langs[i] = languageCodeConverter.fromLanguageCode(langCodes.get(i));
            }
            System.out.println(langs.length);
            JList langList = new JList<>(langs);

            langList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JScrollPane scrollPane = new JScrollPane(langList);
            languagePanel.add(scrollPane);
            JPanel mainPanel = new JPanel();

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = "hi";
                    String country = countryField.getText();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new CanadaTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(buttonPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(countryBox);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
    }
}
