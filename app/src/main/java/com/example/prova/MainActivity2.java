package com.example.prova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

//  inicialzacao das variaveis para a apresentacao dos dados
//   dados de texto dos graficos
    String[] faixaEtaria = {"até 19 anos","20 até 59 anos"," 60+ anos"};
    String contaminacao = "Porcentagem de contaminados";
    float total,total2,p1,p2,p3;
//  variavel para setar os graficos    
    AnyChartView graficoTotal,graficoParcial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//      pegando as informacoes passadas da atividade 1 apos o click do botao estatistica
        Intent intent = this.getIntent();
        total = intent.getFloatExtra("txtTotal",0);
        total2 = intent.getFloatExtra("txtTotal2",0);
        p1 = intent.getFloatExtra("txtP1",0);
         p2 = intent.getFloatExtra("txtP2",0);
         p3 = intent.getFloatExtra("txtP3",0);

         System.out.println(total);
//       colocando os graficos nas variaveis

        graficoTotal = findViewById(R.id.graficoTotal);
        APIlib.getInstance().setActiveAnyChartView(graficoTotal);

        Pie pie  = AnyChart.pie3d();
        List<DataEntry> dados = new ArrayList<>();
        dados.add(new ValueDataEntry(contaminacao,total));
        dados.add(new ValueDataEntry("Nao contaminados",total2));
        pie.data(dados);
        pie.title("Porcentagem total");
        graficoTotal.setChart(pie);

        graficoParcial = findViewById(R.id.graficoParcial);
        APIlib.getInstance().setActiveAnyChartView(graficoParcial);

        Pie pie2 = AnyChart.pie3d();
        List<DataEntry> dados2 = new ArrayList<>();
        dados2.add(new ValueDataEntry(faixaEtaria[0],p1));
        dados2.add(new ValueDataEntry(faixaEtaria[1],p2));
        dados2.add(new ValueDataEntry(faixaEtaria[2],p3));
        pie2.data(dados2);
        pie2.title("porcentagem parcial");
        graficoParcial.setChart(pie2);
    }


}