package com.example.prova;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

//  inicializacao das variaveis
  private Button btnAdicionar, btnExcluir, btnEstatistica;
  private EditText txtNome,txtIdade;
  private RadioGroup rbdIgg;
  private RadioButton rbdPositivo, rbdNegtivo;
  private Spinner spnSexo;
  private ListView lstPacientes;

  float porcentagem[];
  adaptador adaptador;
    ArrayList<listPacientes> listPacientes;
    List<paciente> vetPaciente;
    int codPaciente = 0;
    int sexo = 1, igg = -1;

//  variaveis para apresentar o percentual na activity2


//  variavel da mensagem de alerta
    AlertDialog.Builder builder;

//    adiciona pacientes na lista criada a cima
    public void addPacientes(int cod, String nome, int idade, int sexo, int igg){
        vetPaciente.add(new paciente(cod,nome,idade,sexo,igg));

    }
//    vai preencher o vetor porcentagem com as informacoes para passar para a activity 2
    public void setPerc(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//      importando todos os componentes da atividade 1
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnExcluir = findViewById(R.id.btnExcluir);
        btnEstatistica = findViewById(R.id.btnEstatistica);
        txtNome = findViewById(R.id.txtNome);
        txtIdade = findViewById(R.id.txtIdade);
        rbdIgg = findViewById(R.id.rgbIgg);
        spnSexo = findViewById(R.id.spnSexo);
        lstPacientes = findViewById(R.id.lstPacientes);
        rbdPositivo = findViewById(R.id.rbdPositivo);
        rbdNegtivo = findViewById(R.id.rbdNegativo);
        this.vetPaciente = new ArrayList<>();


//      inicializando a lista de pacientes
        listPacientes = new ArrayList<>();

//      Fazendo o adapter da lista
        adaptador  = new adaptador(this, R.layout.layout_pacientes,listPacientes);
        lstPacientes.setAdapter(adaptador);

//      Funcionalidades da lista
        lstPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout layoutIS = parent.getAdapter().getView(position, view, parent).findViewById(R.id.layoutIS);
                if (layoutIS.getVisibility() == View.INVISIBLE){
                    layoutIS.setVisibility(View.VISIBLE);
                }else {
                    layoutIS.setVisibility(View.INVISIBLE);
                }
            }
        });

//      funcionalidade de click longo na lista
        lstPacientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               String nome =  listPacientes.get(position).getNome();
//             Criacao do builder para a escolha do usuario
               builder = new AlertDialog.Builder(MainActivity.this);
               builder.setTitle("Aviso de Exclusão!!");
               builder.setMessage("Você deseja excluir o paciente " + nome.toString() + " da lista?");
               builder.setPositiveButton("Excluir item", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       listPacientes.remove(position);
                       vetPaciente.remove(position);
                       adaptador.notifyDataSetChanged();
                   }
               });
               builder.setNegativeButton("Não excluir", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               builder.show();
                return false;
            }
        });

//      Funcionalidade do botao excluir
    btnExcluir.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//          Criacao do builder para a escolha do usuario
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Aviso de Exclusão!!");
            builder.setMessage("Você deseja excluir todos os items da lista?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listPacientes.removeAll(listPacientes);
                    vetPaciente.removeAll(vetPaciente);
                    adaptador.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
            adaptador.notifyDataSetChanged();
        }
    });
//      inicializa o radiogroup sem nenhum radiobutton estar selecionado
        rbdIgg.clearCheck();

//      botao estatistica, redireciona para a atividade 2
        btnEstatistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float total = 0,total2 = 0;
                float  p1=0, p2=0, p3=0;

                Intent intent;
                intent = new Intent(MainActivity.this, MainActivity2.class);
//              chama a funcao para preencher o vetor porcentagem

                for(int i = 0; i < listPacientes.size();i++){
                    if(listPacientes.get(i).getImg() == 0){
                        total++;
                        if(listPacientes.get(i).getIdade() <= 19){
                            p1++;
                        }else if(listPacientes.get(i).getIdade() <= 59){
                            p2++;
                        }else if(listPacientes.get(i).getIdade() >= 60){
                            p3++;
                        }
                    }else{
                        total2++;

                    }
                }

                if(listPacientes.isEmpty()){
                    Toast.makeText(MainActivity.this, "Para ver as estatísticas você precisa preencher a lista!!",Toast.LENGTH_LONG).show();
                }else{
//                  calculando as porcentagens
                    p1 = p1/listPacientes.size();
                    p2 = p2/listPacientes.size();
                    p3 = p3/listPacientes.size();
                    total =total/listPacientes.size();
                    total2 = total2/listPacientes.size();

//                  mandando as variaveis para a segunda atividade
                    intent.putExtra("txtTotal",total);
                    intent.putExtra("txtTotal2",total2);
                    intent.putExtra("txtP1",p1);
                    intent.putExtra("txtP2",p2);
                    intent.putExtra("txtP3",p3);
//                  inicializando a atividade 2
                    startActivity(intent);

                }
                }
            });


//        botao adicionar, vai adicionar as informacoes a lista de pacientes
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int prosseguir = 0;

//          vai verificar se os campos estao vazio e vai emitir um Toast para o usuario para ele preencher os campos
            if(txtNome.getText().toString().isEmpty() || TextUtils.isEmpty(txtIdade.getText().toString()) || rbdIgg.getCheckedRadioButtonId() == -1){
                Toast.makeText(MainActivity.this, "Por favor preencha os campos!!", Toast.LENGTH_LONG).show();
                prosseguir = 1;
            }else{
//              vai ver se o radiobutton positivo esta selecionado e vai adquirir um valor a variavel igg para ser passado para a class paciente e depois para a lista
                if(rbdPositivo.isChecked()){
                    igg = 0;
                }else{
                    igg = 1;
                }
//              vai verificar se o item escolhido no spinner e Masculino e vai adquirir um valor a variavel sexo para ser passado para a class paciente e depois para a lista
                if(spnSexo.getSelectedItem().equals("Masculino")){
                    sexo = 0;
                }else{
                    sexo = 1;
                }
//           vai verificar se nao foi inserido uma idade fora do padrao da sociedade
                if( Integer.valueOf(txtIdade.getText().toString()) > 110){
                    txtIdade.setText("");
                    txtIdade.setFocusable(true);
                    Toast.makeText(MainActivity.this, "Por favor preencha com uma idade valida!!", Toast.LENGTH_LONG).show();
                    prosseguir = 1;
                }
//              Vai seguir o codigo caso as validacoes nao ocorram
                if(prosseguir == 0) {
//              vai adicionar os dados na lista atraves da chamada da funcao addPacientes
                    addPacientes(codPaciente, txtNome.getText().toString(), Integer.valueOf(txtIdade.getText().toString()), sexo, igg);

//              vai pegar o ultimo item da classe pacientes e vai colocar dentro da classe listPacientes para aparecer na lista
                    listPacientes p = new listPacientes();
                    p.setNome(vetPaciente.get(vetPaciente.size() - 1).nome);
                    p.setImg(vetPaciente.get(vetPaciente.size() - 1).igg);
                    p.setIdade(vetPaciente.get(vetPaciente.size()-1).idade);
                    p.setSexo(vetPaciente.get(vetPaciente.size()-1).sexo);
                    listPacientes.add(p);

                    adaptador.notifyDataSetChanged();

//              limpa os dados preenchidos
                    txtIdade.setText("");
                    txtNome.setText("");
                    rbdIgg.clearCheck();
                    codPaciente++;
                }
            }
            }
        });
    }
}

