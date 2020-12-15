package com.example.prova;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class adaptador extends ArrayAdapter<listPacientes> {

    private ArrayList<listPacientes> pacientesArray;
    private Context contexto;
    private int recurso;

    private Integer[] imagens = {
            R.drawable.positive_igg,
            R.drawable.negative_igg
    };
    private String[] sexo = {
            "Masculino",
            "Feminino"
    };

    public adaptador(@NonNull Context context, int resource, @NonNull ArrayList<listPacientes> listPacientesArray){
        super(context,resource,listPacientesArray);
//      construtor do adaptador
        this. contexto = context;
        this.pacientesArray = listPacientesArray;
        this.recurso = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        ViewHolder holder;

//        inflando o arquivo personalizado na view
        if (convertView==null) {
            LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
            convertView = inflater.inflate(recurso, parent, false);

            holder = new ViewHolder();

            holder.nome = convertView.findViewById(R.id.txtNomePaciente);
            holder.img = convertView.findViewById(R.id.imgResultado);
            holder.idade = convertView.findViewById(R.id.txtIdadePaciente);
            holder.sexo = convertView.findViewById(R.id.txtSexoPaciente);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();

            listPacientes pacientes = pacientesArray.get(position);
//          vai colocar o nome do paciente na lista
             holder.nome.setText(pacientes.getNome());
//             coloca a imagem certa de acordo com o IGG
             holder.img.setImageResource(imagens[pacientes.getImg()]);
//          Acrescenta o sexo porem ele vai ser passado invisivel devido o layout desta parte estar invisivel num primeiro momento
            holder.sexo.setText(sexo[pacientes.getSexo()]);
//           Acrescenta a idade porem ele vai ser passado invisivel devido o layout desta parte estar invisivel num primeiro momento
            holder.idade.setText(String.valueOf(pacientes.getIdade()));
        }

        return convertView;

    }

    static class ViewHolder {
        TextView nome;
        ImageView img;
        TextView idade;
        TextView sexo;

    }
}
