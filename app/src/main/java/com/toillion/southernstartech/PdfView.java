package com.toillion.southernstartech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;

public class PdfView extends AppCompatActivity {

    private PDFView pdfView;
    private String pdfName;

    // This activity is used to load the PDFs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        // Connect to the pdf view in res/layout/activity_pdf_view.xml
        pdfView = findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // We passed the name of the work type which we will use to get a pdf
            pdfName = bundle.getString("name");
        }

        if (pdfName != null) {

            // Properly format the pdfName
            pdfName = formatePdfName(pdfName);

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

    }

    // Format the file names to be all lowercase with underscores instead of spaces and end with .pdf
    private String formatePdfName(String name) {
        String pdfFileName = name.replace(' ', '_');
        pdfFileName += ".pdf";
        pdfFileName = pdfFileName.toLowerCase();
        return pdfFileName;
    }

    private void pdfLoadError() {
        Toast.makeText(getApplicationContext(), "Could not find a pdf with the name " + pdfName, Toast.LENGTH_LONG).show();
    }
}
