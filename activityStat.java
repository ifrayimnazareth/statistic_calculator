package com.example.statisticcalt;

//import com.example.standarexp.MainActivity;

import java.util.ArrayList;

import android.os.Bundle;
//import android.app.Activity;
//import android.view.Menu;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private EditText display;
    private double firstOperand = 0;
    private double secondOperand = 0;
    private String operator = "";
    private boolean isOperatorPressed = false;
    private double memory = 0;
    private ArrayList<Double> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		display = (EditText)findViewById(R.id.editText);
		data = new ArrayList<Double>();

        // Register click listeners for all buttons
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.button_add, R.id.button_sigma,
                R.id.button_sigmasqrt, R.id.button_xbar, R.id.button_equals,
                R.id.button_c, R.id.button_cad, R.id.button_backspace,
                R.id.button_f_e, R.id.button_exp, R.id.button_x_squared,
                R.id.button_sigma_n, R.id.button_sigma_nMinOne,
                R.id.button_mc, R.id.button_mr, R.id.button_ms, R.id.button_m_plus, R.id.button_m_minus,
                R.id.button_decimal
        };
        
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }
	}
	
	@SuppressLint("NewApi")
	private final View.OnClickListener buttonClickListener = new View.OnClickListener(){
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		@Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();

            if (buttonText.matches("[0-9]")) { // If a number is pressed
                if (isOperatorPressed) {
                    display.setText("");
                    isOperatorPressed = false;
                }
                display.append(buttonText);
            } else if (buttonText.equals(".")) { // If decimal point is pressed
                String currentText = display.getText().toString();
                if (!currentText.isEmpty() && !currentText.contains(".")) {
                    display.append(".");
                } else if (currentText.isEmpty()) {
                    display.setText("0."); // Automatically add '0.' if the display is empty
                }
            } else if (buttonText.equals("C")) { // Clear all
                display.setText("");
                firstOperand = secondOperand = 0;
                operator = "";
            } else if (buttonText.equals("CAD")) { // Clear entry
                display.setText("");
            } else if (buttonText.equals("←")) { // Backspace
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (buttonText.equals("±")) { // Negate
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    double value = Double.parseDouble(currentText);
                    display.setText(String.valueOf(-value));
                }
            } else if (buttonText.equals("F-E")) { // Square root
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    double value = Double.parseDouble(currentText);
                    try {
                        // Menghitung e^x
                        double result = Math.exp(value);
                        // Menampilkan hasil
                        display.setText(String.valueOf(result));
                    } catch (NumberFormatException e) {
                        // Menangani kesalahan jika input tidak valid
                        display.setText("Input tidak valid");
                    }
                }
            } else if (buttonText.equals("x̄")) { // Reciprocal
            	if (!data.isEmpty()) {
                    double sum = 0;
                    for (double value : data) {
                        sum += value;
                    }
                    double mean = sum / data.size();
                    display.setText(String.valueOf(mean));
                } else {
                    display.setText("No data");
                }
            } else if (buttonText.equals("x²")) { // Reciprocal
            	if (!data.isEmpty()) {
                    double sum = 0;
                    for (double value : data) {
                        sum += value; // Menjumlahkan semua nilai
                    }
                    double mean = sum / data.size(); // Menghitung rata-rata (x-bar)
                    double meanSquared = Math.pow(mean, 2); // Menghitung x-bar kuadrat
                    display.setText(String.valueOf(meanSquared)); // Menampilkan hasil
                } else {
                    display.setText("No data"); // Jika tidak ada data
                }
            } else if (buttonText.equals("Σx")) {
//                String currentText = display.getText().toString();
                if (!data.isEmpty()) {
                    double sum = 0;
                    for (double value : data) {
                        sum += value;
                    }
                    display.setText(String.valueOf(sum));
                } else {
                    display.setText("No data");
                }
            } else if (buttonText.equals("Σx²")) {
            	if (!data.isEmpty()) {
                    double sumOfSquares = 0;
                    for (double value : data) {
                        sumOfSquares += value * value;
                    }
                    display.setText(String.valueOf(sumOfSquares));
                } else {
                    display.setText("No data");
                }
            } else if (buttonText.equals("σn")) {
            	if (!data.isEmpty()) {
                    double mean = 0;
                    for (double value : data) {
                        mean += value;
                    }
                    mean /= data.size();

                    double variance = 0;
                    for (double value : data) {
                        variance += Math.pow(value - mean, 2);
                    }
                    variance /= data.size();

                    double stdDev = Math.sqrt(variance);
                    display.setText(String.valueOf(stdDev));
                } else {
                    display.setText("No data");
                }
            } else if (buttonText.equals("σn-1")) {
            	if (data.size() > 1) {
                    double mean = 0;
                    for (double value : data) {
                        mean += value;
                    }
                    mean /= data.size();

                    double variance = 0;
                    for (double value : data) {
                        variance += Math.pow(value - mean, 2);
                    }
                    variance /= (data.size() - 1);

                    double stdDev = Math.sqrt(variance);
                    display.setText(String.valueOf(stdDev));
                } else {
                    display.setText("Not enough data");
                }
            } else if (buttonText.equals("Add")) {
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    try {
                        double value = Double.parseDouble(currentText);
                        data.add(value);
                        display.setText("");
                        Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        display.setText("Invalid input");
                    }
                } else {
                    display.setText("Input empty");
                }
            } else if (buttonText.equals("Exp")) { // Percent
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                	try {
                        double value = Double.parseDouble(currentText);
                        // Menghitung e^x
                        double result = Math.exp(value);
                        // Menampilkan hasil
                        display.setText(String.valueOf(result));
                    } catch (NumberFormatException e) {
                        // Menangani kesalahan jika input tidak valid
                        display.setText("Input tidak valid");
                    }
                }
            } else if (buttonText.equals("=")) { // Calculate result
                String currentText = display.getText().toString();
                if (!currentText.isEmpty()) {
                    try {
                        secondOperand = Double.parseDouble(currentText);
                        double result = calculateResult(firstOperand, secondOperand, operator);
                        display.setText(String.valueOf(result));
                        operator = "";
                    } catch (NumberFormatException e) {
                        display.setText("Input tidak valid");
                    }
                } else {
                    display.setText("Input kosong");
                }
            } else if (buttonText.equals("MC")) { // Memory Clear
                memory = 0;
                Toast.makeText(MainActivity.this, "Memory Cleared", Toast.LENGTH_SHORT).show();
            } else if (buttonText.equals("MR")) { // Memory Recall
                display.setText(String.valueOf(memory));
            } else if (buttonText.equals("MS")) { // Memory Store
                try {
                    memory = Double.parseDouble(display.getText().toString());
                    Toast.makeText(MainActivity.this, "Memory Stored", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            } else if (buttonText.equals("M+")) { // Memory Plus
                try {
                    double currentValue = Double.parseDouble(display.getText().toString());
                    memory += currentValue;
                    Toast.makeText(MainActivity.this, "Memory Added", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            } else if (buttonText.equals("M-")) { // Memory Minus
                try {
                    double currentValue = Double.parseDouble(display.getText().toString());
                    memory -= currentValue;
                    Toast.makeText(MainActivity.this, "Memory Subtracted", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            } else { // Operator is pressed
                firstOperand = Double.parseDouble(display.getText().toString());
                operator = buttonText;
                isOperatorPressed = true;
            }
        }

	};
	
	private double calculateResult(double num1, double num2, String operator) {
    	if (operator.equals("+")) {
            return num1 + num2;
        } else if (operator.equals("-")) {
            return num1 - num2;
        } else if (operator.equals("*")) {
            return num1 * num2;
        } else if (operator.equals("/")) {
            if (num2 != 0) {
                return num1 / num2;
            } else {
                return Double.NaN; // Handle division by zero
            }
        } else {
            return 0;
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
