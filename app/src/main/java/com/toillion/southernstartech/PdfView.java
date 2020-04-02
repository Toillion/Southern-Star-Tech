package com.toillion.southernstartech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class PdfView extends AppCompatActivity {

    private PDFView pdfView;
    private String pdfName, name;
    private Button next, previous;
    private TextView title;
    private int index = 1;

    // This activity is used to load the PDFs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        // Connect to the pdf view in res/layout/activity_pdf_view.xml
        pdfView = findViewById(R.id.pdfView);
        next = findViewById(R.id.nextPdfBtn);
        previous = findViewById(R.id.previousPdfBtn);
        title = findViewById(R.id.workTypeTitleTV);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // We passed the name of the work type which we will use to get a pdf
            name = bundle.getString("name");
        }

        if (name != null) {
            title.setText(name);
            setButtonState();
            loadPdf(name, index);
        } else {
            Toast.makeText(getApplicationContext(), "Could not find a pdf", Toast.LENGTH_LONG).show();

        }

    }

    private void loadPdf(String name, int i) {
        // Properly format the pdfName
        pdfName = formatePdfName(name, i);

        // this is where we actually get the pdf from the assets folder and set it
        pdfView.fromAsset(pdfName)
                .password(null)//enter password if PDF is password protected
                .defaultPage(0)//set the default page
                .enableSwipe(true)//enable the swipe to change page
                .swipeHorizontal(false)//set horizontal swipe to false
                .enableDoubletap(true)//double tap to zoom
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        // If unable to load a pdf with the given name, display an error
                        pdfLoadError();
                    }
                })
                .load();
    }

    // Format the file names to be all lowercase with underscores instead of spaces and end with .pdf
    private String formatePdfName(String name, int i) {
        String pdfFileName = name.replace(' ', '_');
        pdfFileName += "_" + i;
        pdfFileName += ".pdf";
        pdfFileName = pdfFileName.toLowerCase();
        return pdfFileName;
    }

    private void pdfLoadError() {
        Toast.makeText(getApplicationContext(), "Could not find a pdf with the name " + pdfName, Toast.LENGTH_LONG).show();
    }

    // Depending on whether or not there is a next/prev pdf, enable/disable the buttons
    private void setButtonState() {

        if (index <= 1) {
            previous.setEnabled(false);
        } else {
            previous.setEnabled(true);
        }
        try {
            boolean nextFileExists = Arrays.asList(Objects.requireNonNull(getResources().getAssets().list(""))).contains(formatePdfName(name, index + 1));
            if (nextFileExists) {
                next.setEnabled(true);
            } else {
                next.setEnabled(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            next.setEnabled(false);
        }
    }

    // Get the next pdf when the button is clicked
    public void nextClickHandler(View v) {
        ++index;
        setButtonState();
        loadPdf(name, index);
    }

    // Get the prev pdf when the button is clicked
    public void previousClickHandler(View v) {
        --index;
        setButtonState();
        loadPdf(name, index);
    }
}
