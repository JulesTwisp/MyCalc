package br.senai.sp.informatica.mycalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editDisplay;
    Double valorDisplay = 0.0;
    Double valorPorcentagem = 0.0;
    Double resultado = 0.0;
    String stringDisplay;
    String operacao = "C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        incializarComponentes();
    }

    private void incializarComponentes() {
        editDisplay = (EditText) findViewById(R.id.edit_display);

    }

    public void numberOnClick(View view) {
        //Guarda o botão clicado
        Button button = (Button) view;
        //Verifica o texto atual do display
        stringDisplay = editDisplay.getEditableText().toString();
        // Verifica se a string é igual a zero
        if (stringDisplay.equals(getString(R.string.zero))) {
            //Se sim, coloca no display o numero do
            //botao que foi acionado
            editDisplay.setText(button.getText());
        } else {
            //se já tem numeros diferent4es de 0
            //no display, apenas acrescenta
            //o proximo numero
            editDisplay.append(button.getText());
        }
    }

    public void ceOnClick(View view) {
        editDisplay.setText("");
        valorDisplay = 0.0;
        valorPorcentagem = 0.0;
        resultado = 0.0;
        stringDisplay = "";
        operacao = "C";
    }

    public void bsOnClick(View view) {
        try {
            // Pega o valor atual no display
            String currentDisplay = editDisplay.getEditableText().toString();
            // Remove o ultimo caractere
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
            // Se o valor não estiver vazio
            if (!currentDisplay.isEmpty()) {
                // Grava o valor atual
                editDisplay.setText(currentDisplay);
            } else {
                // caso haja algo no display
                // escreve zero
                editDisplay.setText(R.string.zero);
            }
        } catch (StringIndexOutOfBoundsException e) {
            Toast.makeText(this, getString(R.string.erro_backspace), Toast.LENGTH_SHORT).show();
            Log.e("CALC", e.getMessage());
        }
    }

    public void opOnClick(View view) {
        try {
            // Armazena o valor atual do display com base no id da view
            stringDisplay = editDisplay.getEditableText().toString();
            valorDisplay = Double.parseDouble(stringDisplay);
            // com base no id da view determina qual operaçãO deverá ser realizada
            switch (view.getId()) {
                case R.id.button_divisao:
                    operacao = "D";
                    // zera o valor do display para que o usuário consiga digitar
                    // o próximo valor
                    editDisplay.setText(getString(R.string.zero));
                    break;
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.valor_invalido), Toast.LENGTH_SHORT).show();
        }
    }

    public void igualOnClick(View view) {
        // Verifica qual a operação selecionada
        switch (operacao) {
            case "D":
                // Recupera o valor do display (segundo valor)
                // realiza a operação aritmetica
                Double divisor = Double.parseDouble(editDisplay.getEditableText().toString());
                resultado = valorDisplay / divisor;
                break;
        }
        if (!resultado.isInfinite()) {
            editDisplay.setText(String.valueOf(resultado));
        } else {
            Toast.makeText(this, getString(R.string.div_zero), Toast.LENGTH_SHORT).show();
        }
    }

}
