package com.realllydan.hashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private final String[] items = {"MD5", "SHA-1", "SHA-256"};
    private final static int FIRST_LIST_ITEM = 0;

    private View rootLayout;
    private TextInputLayout tilInputString;
    private AutoCompleteTextView hashingMethodDropdown;
    private TextView tvHashTitle, tvCalcHash, tvInfo;
    private Button btnGenHash;
    private String userSelectedHashingMethod = items[FIRST_LIST_ITEM];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUi();
    }

    private void setupUi() {
        rootLayout = findViewById(R.id.rootLayout);
        tilInputString = findViewById(R.id.til_input_string);
        tvCalcHash = findViewById(R.id.tv_calc_hash);
        btnGenHash = findViewById(R.id.btn_gen_hash);
        tvHashTitle = findViewById(R.id.tv_hash_title);
        tvInfo = findViewById(R.id.tv_info);

        setupHashingMethodDropdown();
        handleGenButtonClick();

        hideHashTextViews();
        setupGenHashTextView();
    }

    private void setupHashingMethodDropdown() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, items);

        hashingMethodDropdown = findViewById(R.id.actv_hashing_method);
        hashingMethodDropdown.setAdapter(arrayAdapter);
        hashingMethodDropdown.setText(arrayAdapter.getItem(FIRST_LIST_ITEM).toString(), false);

        hashingMethodDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userSelectedHashingMethod = items[position];
            }
        });
    }

    private String retrieveUserTypedString() {
        return tilInputString.getEditText().getText().toString();
    }

    private void validateInput(String userTypedInputString) {

        if (userTypedInputString.isEmpty()) {
            handleEmptyInput();
        } else {
            generateAndDisplayHash(userTypedInputString);
        }
    }

    private void generateAndDisplayHash(String userTypedInputString) {
        String generatedHash = Hash.getEncryptedMessage(userSelectedHashingMethod, userTypedInputString);

        showHashTextViews();
        tvHashTitle.setText(userSelectedHashingMethod);
        tvCalcHash.setText(generatedHash);
    }

    private void handleGenButtonClick() {
        btnGenHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tilInputString.isErrorEnabled()) {
                    tilInputString.setErrorEnabled(false);
                    validateInput(retrieveUserTypedString());
                } else {
                    validateInput(retrieveUserTypedString());
                }
            }
        });
    }

    private void handleEmptyInput() {
        tilInputString.setError(getString(R.string.til_empty_input_error));
    }

    private void showHashTextViews() {
        tvInfo.setVisibility(View.VISIBLE);
        tvHashTitle.setVisibility(View.VISIBLE);
        tvCalcHash.setVisibility(View.VISIBLE);
    }

    private void hideHashTextViews() {
        tvInfo.setVisibility(View.GONE);
        tvHashTitle.setVisibility(View.GONE);
        tvCalcHash.setVisibility(View.GONE);
    }

    private void setupGenHashTextView() {
        tvCalcHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextToClipboard(tvCalcHash.getText().toString());
                displayCopySuccess();
            }
        });
    }

    private void copyTextToClipboard(String textToCopy) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Hashed Message", textToCopy);
        clipboard.setPrimaryClip(clip);
    }

    private void displayCopySuccess() {
        Snackbar.make(rootLayout, "Hash copied to the clipboard!", Snackbar.LENGTH_LONG).show();
    }
}