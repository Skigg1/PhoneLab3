package com.example.lab3;

import android.os.Bundle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4;
    private boolean[] selectedAnimals;
    private String[] animalOptions = {
            "Солнце", "Меркурий", "Венера", "Земля", "Марс",
            "Юпитер", "Сатурн", "Уран", "Нептун", "Плутон"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupButtonListeners();

        selectedAnimals = new boolean[animalOptions.length];
    }
    private void initializeViews() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button6);
    }

    private void setupButtonListeners() {
        //на 2 секунды
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "кнопка номер 1 нажата", Toast.LENGTH_SHORT).show();
            }
        });
        //на 3.5 секунды
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "кнопка номер 2 нажата", Toast.LENGTH_LONG).show();
            }
        });
        // Кнопка 3 - Диалоговое окно с выбором
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogButton3();
            }
        });
        // Кнопка 4 - Тест с множественным выбором
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalTestDialog();
            }
        });
    }

    private void showDialogButton3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("кнопка 3")
                .setMessage("Вы хотите сделать текст кнопок красным?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Делаем цвет текста красным у всех кнопок
                        button1.setTextColor(Color.RED);
                        button2.setTextColor(Color.RED);
                        button3.setTextColor(Color.RED);
                        button4.setTextColor(Color.RED);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Диалог закрыт", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAnimalTestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Тест: Выбери планеты солнечной системы")
                .setCancelable(false)
                .setMultiChoiceItems(animalOptions, selectedAnimals, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedAnimals[which] = isChecked;
                    }
                })
                .setPositiveButton("Проверить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkAnimalAnswers();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Проверка ответов в тесте
     */
    private void checkAnimalAnswers() {
        boolean[] correctAnswers = {false, true, true, true, true, true, true, true, true, false};

        boolean allCorrect = true;

        // Проверяем каждый выбранный пункт
        for (int i = 0; i < selectedAnimals.length; i++) {
            if (selectedAnimals[i] != correctAnswers[i]) {
                allCorrect = false;
                break;
            }
        }

        if (allCorrect) {
            Toast.makeText(this, "Поздравляем! Все ответы верные!", Toast.LENGTH_LONG).show();
        } else {
            // Скрываем все кнопки при неверном ответе
            button1.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);

            Toast.makeText(this, "Неверный ответ! Кнопки скрыты.", Toast.LENGTH_LONG).show();

            // Через 3 секунды возвращаем кнопки
            button1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    button4.setVisibility(View.VISIBLE);
                }
            }, 3000);
        }

        // Сбрасываем выбранные элементы
        resetSelectedAnimals();
    }

    /**
     * Сброс выбранных животных
     */
    private void resetSelectedAnimals() {
        for (int i = 0; i < selectedAnimals.length; i++) {
            selectedAnimals[i] = false;
        }
    }

}