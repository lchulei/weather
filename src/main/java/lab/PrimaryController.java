package lab;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class PrimaryController implements Initializable {

    @FXML
    private TextField juneFirstValue;
    
    @FXML
    private TextField juneSecondValue;
    
    @FXML
    private TextField novemberFirstValue;
    
    @FXML
    private TextField novemberSecondValue;
    
    @FXML
    private TextField novemberThirdValue;
    
    @FXML
    private RadioButton moreDefined;
    
    @FXML
    private RadioButton moreUndefined;
    
    @FXML
    private Button calculateEntropy;
    
    @FXML
    private Label errorMessage;
    
    @FXML
    private TextField entropyJune;
    
    @FXML
    private TextField entropyNovember;

    public static double log2(double n) {
        return (Math.log(n) / Math.log(2));
    }

    private double entropy(List<Double> probabilities) {
        double entropy = 0;
        for (Double p : probabilities) {
            if (p > 0) {
                entropy += p * log2(p);
            }
        }
        return -entropy;
    }

    @FXML
    private void calculateEntropy() {
        try {
            errorMessage.setVisible(false);
            entropyJune.clear();
            entropyNovember.clear();

            if (juneFirstValue.getText() == null || juneFirstValue.getText().isEmpty()) {
                errorMessage.setVisible(true);
                errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
                return;
            }
            if (juneSecondValue.getText() == null || juneSecondValue.getText().isEmpty()) {
                errorMessage.setVisible(true);
                errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
                return;
            }
            if (novemberFirstValue.getText() == null || novemberFirstValue.getText().isEmpty()) {
                errorMessage.setVisible(true);
                errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
                return;
            }
            if (novemberSecondValue.getText() == null || novemberSecondValue.getText().isEmpty()) {
                errorMessage.setVisible(true);
                errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
                return;
            }
            if (novemberThirdValue.getText() == null || novemberThirdValue.getText().isEmpty()) {
                errorMessage.setVisible(true);
                errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
                return;
            }

            Double pRainJune = Double.parseDouble(juneFirstValue.getText().replace(',', '.'));
            Double pNoRainJune = Double.parseDouble(juneSecondValue.getText().replace(',', '.'));
            Double pRainNovember = Double.parseDouble(novemberFirstValue.getText().replace(',', '.'));
            Double pSnowNovember = Double.parseDouble(novemberSecondValue.getText().replace(',', '.'));
            Double pNoPrecipitationNovember = Double.parseDouble(novemberThirdValue.getText().replace(',', '.'));

            List<Double> probabilitiesJune = new ArrayList<>();
            List<Double> probabilitiesNovember = new ArrayList<>();

            probabilitiesJune.add(pRainJune);
            probabilitiesJune.add(pNoRainJune);

            probabilitiesNovember.add(pRainNovember);
            probabilitiesNovember.add(pSnowNovember);
            probabilitiesNovember.add(pNoPrecipitationNovember);

            Double entropyJ = entropy(probabilitiesJune);
            Double entropyN = entropy(probabilitiesNovember);

            entropyJ = entropyJ == 0 ? 0 : entropyJ;
            entropyN = entropyN == 0 ? 0 : entropyN;

            Double totalJune = pRainJune + pNoRainJune;
            Double totalNovember = pRainNovember + pSnowNovember + pNoPrecipitationNovember;

            this.entropyJune.setText(String.valueOf(entropyJ));
            this.entropyNovember.setText(String.valueOf(entropyN));

            if (totalJune > 1 || totalNovember > 1) {
                errorMessage.setVisible(true);
                errorMessage.setText("Помилка: Сума значень в кошику має бути не більше 1");
            } else {
                if (moreDefined.isSelected()) {
                    if (entropyJ < entropyN) {
                        errorMessage.setVisible(true);
                        errorMessage.setText("Погода 15 червня вважається більш визначеною.");
                    } else if (entropyJ > entropyN) {
                        errorMessage.setVisible(true);
                        errorMessage.setText("Погода 15 листопада вважається більш визначеною.");
                    } else {
                        errorMessage.setVisible(true);
                        errorMessage.setText("Погода 15 червня та 15 листопада мають однаковий рівень визначеності.");
                    }
                } else if (moreUndefined.isSelected()) {
                    if (entropyJ > entropyN) {
                        errorMessage.setVisible(true);
                        errorMessage.setText("Погода 15 червня вважається більш невизначеною.");
                    } else if (entropyJ < entropyN) {
                        errorMessage.setVisible(true);
                        errorMessage.setText("Погода 15 листопада вважається більш невизначеною.");
                    } else {
                        errorMessage.setVisible(true);
                        errorMessage.setText("Погода 15 червня та 15 листопада мають однаковий рівень визначеності.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setVisible(true);
            errorMessage.setText("Будь ласка, введіть коректні значення ймовірностей.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        moreDefined.setToggleGroup(toggleGroup);
        moreUndefined.setToggleGroup(toggleGroup);
        moreDefined.setSelected(true);
    }
}
