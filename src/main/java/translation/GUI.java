package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Arrays;
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
            JComboBox langBox = new JComboBox<>();
            List<String> countryCodes = json.getCountryCodes();
            List<String> langCodes = json.getLanguageCodes();
            for (int i = 0; i < langCodes.size(); i++) {
                langBox.addItem(languageCodeConverter.fromLanguageCode(langCodes.get(i)));
            }
            String[] countries = new String[countryCodes.size()];
            for (int i = 0; i < countryCodes.size(); i++) {
                countries[i] = countryCodeConverter.fromCountryCode(countryCodes.get(i));
            }
            System.out.println(countries.length);
            JList countryList = new JList<>(countries);

            countryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JPanel langPanel = new JPanel();
            langPanel.add(langBox);

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Language:"));
            JScrollPane scrollPane = new JScrollPane(countryList);
            countryPanel.add(scrollPane);
            JPanel mainPanel = new JPanel();

            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);
            countryList.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    if (countryList.getSelectedIndices().length == 0) {
                        resultLabel.setText("No language selected!");
                        return;
                    }
                    String country = countryList.getModel().getElementAt(countryList.getSelectedIndices()[0])
                            .toString();
                    String language = langBox.getSelectedItem().toString();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = json.translate(countryCodeConverter.fromCountry(country),
                            languageCodeConverter.fromLanguage(language));
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }
            });
            langBox.addItemListener(new ItemListener() {

                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (countryList.getSelectedIndices().length == 0) {
                        resultLabel.setText("No language selected!");
                        return;
                    }
                    String country = countryList.getModel().getElementAt(countryList.getSelectedIndices()[0])
                            .toString();
                    String language = langBox.getSelectedItem().toString();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = json.translate(countryCodeConverter.fromCountry(country),
                            languageCodeConverter.fromLanguage(language));
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }

            });

            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(langPanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
    }
}
