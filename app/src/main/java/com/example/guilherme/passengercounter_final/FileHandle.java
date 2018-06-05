package com.example.guilherme.passengercounter_final;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileHandle {

    private Context context;

    public FileHandle(Context context) {
        this.context = context;
    }


    public void findExcelFiles(){

        //check if the app folder exists, if not create it!
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "PassengerCounterExcel");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }

        }else{
            //list all files in folder
            String path = Environment.getExternalStorageDirectory().toString()+"/PassengerCounterExcel";
            File directory = new File(path);
            File[] files = directory.listFiles();
            Log.d("Files found", "Size: "+ files.length);

            for (File file: files ) {

                Log.d("Files", "Starting to read each one: ");



            }
            
            
            /*for (int i = 0; i < files.length; i++)
            {
                Log.d("Files", "FileName:" + files[i].getName());
                
                
            }*/
        }

    }

    private void readExcelFIles(File _file){

        try {
            InputStream inputStream = new FileInputStream(_file);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            List<XSSFSheet> sheets = new ArrayList<>(); //Create a list of Sheets
            for(int i=0; i<workbook.getNumberOfSheets(); i++){
                if(!workbook.getSheetAt(i).getSheetName().contains("Sheet")){ //If the Sheet name is not default, insert in the list
                    sheets.add(workbook.getSheetAt(i));
                }
            }

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            //each sheet is a "viagem"
            readExcelWorkbook(sheets , formulaEvaluator);

        }catch (FileNotFoundException e) {
            Log.e("File", "readExcelFile: FileNotFoundException: " + e.getMessage());
        }catch (IOException e){
            Log.e("File", "readExcelFile: Error while reading upstream: " + e.getMessage());
        }

    }

    private void readExcelWorkbook(List<XSSFSheet> _sheets, FormulaEvaluator _formulaEvaluator){
        Log.e("File", "processing the workbooks" );


        for (int i=0; i<_sheets.size();i++){
           readExcelSheet(_sheets.get(i),_formulaEvaluator);
        }


    }

    private void readExcelSheet(XSSFSheet _sheet, FormulaEvaluator _formulaEvaluator){
        Row firstRow = _sheet.getRow(0);
        Row secRow = _sheet.getRow(1);

        //search for the trip columns data
        String onibus;
        int onibus_pos = 0;
        String cor;
        int cor_pos = 1;
        String origem;
        int origem_pos = 3;
        String destino;
        int destino_pos = 4;



        //passenger variables
        String nome;
        int nome_pos = 5;
        String barcode;
        int barcode_pos = 6;
        String quarto;
        int quarto_pos = 7;


        for (int i=0; i<firstRow.getPhysicalNumberOfCells(); i++){
            String aux = getCellAsString(firstRow, i, _formulaEvaluator);
            if(aux.trim().toLowerCase().contains("onibus") ) onibus_pos = i;
            if(aux.trim().toLowerCase().contains("cor") ) cor_pos = i;
            if(aux.trim().toLowerCase().contains("origem") ) origem_pos = i;
            if(aux.trim().toLowerCase().contains("destino") ) destino_pos = i;
            if(aux.trim().toLowerCase().contains("nome") ) nome_pos = i;
            if(aux.trim().toLowerCase().contains("barcode") ) barcode_pos = i;
            if(aux.trim().toLowerCase().contains("quarto") ) quarto_pos = i;
        }
        onibus = getCellAsString(secRow,onibus_pos,_formulaEvaluator);
        cor = getCellAsString(secRow,cor_pos,_formulaEvaluator);
        origem = getCellAsString(secRow,origem_pos,_formulaEvaluator);
        destino = getCellAsString(secRow,destino_pos,_formulaEvaluator);

        // creates a new trip
        DBController controller = new DBController(context);
        controller.InsereDbViagem(onibus,cor,origem,destino);
        String id_viagem = controller.selecionaViagemId(onibus,cor,origem,destino);


        // search for the columns with passengers data
        // The firs row must be with the description data and the others rows must be the actual data
        for (int i = 2;i<_sheet.getPhysicalNumberOfRows(); i++){
            nome = getCellAsString(_sheet.getRow(i) ,nome_pos,_formulaEvaluator);
            barcode = getCellAsString(_sheet.getRow(i) ,barcode_pos,_formulaEvaluator);
            quarto = getCellAsString(_sheet.getRow(i) ,quarto_pos,_formulaEvaluator);

            // put in database
            controller.InsereDbPassageiros(nome,barcode,quarto,id_viagem);
            Log.e("File", "Inserting passengers DB: " + id_viagem + " , " + nome + " , "  + barcode);

        }

        Log.e("File", "end of reading sheet ");

    }

    /**
     * Returns the cell as a string from the excel file
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e("File", "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }

}

