package com.example.caculya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.SortedList;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Double> chisla = new ArrayList();
    ArrayList znaki=new ArrayList();
    ArrayList skobki=new ArrayList();
    String chi= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        TextView output = findViewById(R.id.output);
        output.setText("");
    }
    public void stervseClick(View view) {
        TextView output = findViewById(R.id.output);
        output.setText("");
        chi= "";
        chisla.clear();
        znaki.clear();
        skobki.clear();
        TextView otlad = findViewById(R.id.otlad);
        otlad.setText("");
    }
    public void otlClick(View view) {
        TextView otlad = findViewById(R.id.otlad);
        otlad.setText("");
    }
    public void udalposlClick(View view) {
        TextView output = findViewById(R.id.output);
        TextView otlad = findViewById(R.id.otlad);
        String p = (String) output.getText();
        if (chi.length()==0 && !znaki.isEmpty())
        {
            int z = znaki.size()-1;
            if(!znaki.get(z).equals('(')&&!znaki.get(z).equals(')'))
            {
                if (chisla.get(chisla.size()-1)%1==0)
                    chi = Integer.toString(chisla.get(chisla.size()-1).intValue());
                else
                   chi = Double.toString(chisla.get(chisla.size()-1));
            }
            else
            {
                if(znaki.get(z).equals('(')&&skobki.size()>0)
                {
                    skobki.remove(skobki.size()-1);
                }
                else if(znaki.get(z).equals(')'))
                {
                    skobki.add('(');
                    if(chisla.get(z)!=null)
                    {
                        if (chisla.get(chisla.size()-1)%1==0)
                            chi = Integer.toString(chisla.get(chisla.size()-1).intValue());
                        else
                            chi = Double.toString(chisla.get(chisla.size()-1));
                    }
                }

            }

            chisla.remove(chisla.size()-1);
            znaki.remove(znaki.size()-1);
            output.setText(p.substring(0,p.length()-1));
        }
        else if(chi.length()>0)
        {
            output.setText(p.substring(0,p.length()-1));
            chi = chi.substring(0,chi.length()-1);
        }
        else
            output.setText("");
    }
    public void dobchislo(char ch)
    {
        TextView output = findViewById(R.id.output);
        chi+=ch;
        output.setText(output.getText()+String.valueOf(ch));

    }
    public void dobznak(char zn)
    {
        if (chi!=""||(znaki.get(znaki.size()-1).equals(')')&&zn!=')'))
        {
            if(chi!="") {
                chisla.add(chisla.size(), Double.parseDouble(chi));
                chi = "";
            }
            znaki.add(znaki.size(),zn);
            TextView output = findViewById(R.id.output);
            output.setText(output.getText()+String.valueOf(zn));
        }
        else if(znaki.get(znaki.size()-1).equals(')'))
        {
            chisla.add(null);
            chi="";
            znaki.add(znaki.size(),zn);
            TextView output = findViewById(R.id.output);
            output.setText(output.getText()+String.valueOf(zn));
        }

    }
    public void ravnoClick(View view)
    {
        TextView otlad = findViewById(R.id.otlad);
        if (skobki.size()>0)
        {
            otlad.setText("Закрыты не все скобки!");
            return;
        }
        if (!znaki.isEmpty()&&(chi!=""||znaki.get(znaki.size()-1).equals(')')))
        {
            if(chi!="")
            {
                chisla.add(Double.parseDouble(chi));
                chi = "";
            }
            otlad.setText(otlad.getText()+""+chisla.size()+"(");
            for(int z = 0; z<chisla.size();z++)
                otlad.setText(otlad.getText()+""+chisla.get(z)+" ");
            otlad.setText(otlad.getText()+") "+znaki.size()+"(");
            for (int z =0;z<znaki.size();z++)
                otlad.setText(otlad.getText()+""+znaki.get(z)+" ");
            otlad.setText(otlad.getText()+")");
            poiskskob(0);
            try {
                schet(0,znaki.size(),true);
            }
            catch (Exception e)
            {
                otlad.setText(""+chisla.size()+"(");
                for(int z = 0; z<chisla.size();z++)
                    otlad.setText(otlad.getText()+""+chisla.get(z)+" ");
                otlad.setText(otlad.getText()+") "+znaki.size()+"(");
                for (int z =0;z<znaki.size();z++)
                    otlad.setText(otlad.getText()+""+znaki.get(z)+" ");
                otlad.setText(otlad.getText()+")");
            }
            TextView output = findViewById(R.id.output);
            chi = String.valueOf(chisla.get(0));
            output.setText(output.getText()+"\n="+chi);
            chisla.clear();

        }
        else
            otlad.setText("Чота пошло по пизде");
    }

    public int poiskskob(int indvhod)
    {
        int vih = chisla.size();

        for(int i =indvhod; i<znaki.size();i++)
        {
            if(znaki.get(i).equals('('))
            {
                znaki.remove(i);
                chisla.remove(i);
                try {
                    vih = poiskskob(i);
                    schet(i,vih,true);
                }
                catch (Exception e)
                {
                    TextView otlad = findViewById(R.id.otlad);
                    otlad.setText(otlad.getText()+""+chisla.size()+"(");
                    for(int z = 0; z<chisla.size();z++)
                        otlad.setText(otlad.getText()+""+chisla.get(z)+" ");
                    otlad.setText(otlad.getText()+") "+znaki.size()+"(");
                    for (int z =0;z<znaki.size();z++)
                        otlad.setText(otlad.getText()+""+znaki.get(z)+" ");
                    otlad.setText(otlad.getText()+") "+i+" "+vih);
                }
            }
            else if(znaki.get(i).equals(')'))
            {
                if(chisla.get(i)==null)
                    chisla.remove(i);

                znaki.remove(i);

                return i;
            }
        }
        return 0;
    }
    public void schet(int vhod, int vihod,boolean prov)
    {
        for(int i =vhod; i<vihod;i++)
        {
            if(znaki.get(i).equals('^'))
            {
                chisla.set(i,Math.pow(chisla.get(i),chisla.get(i+1)));
                chisla.remove(i+1);
                znaki.remove(i);
                i--;
                vihod--;
            }
        }
        for(int i =vhod; i<vihod;i++)
        {
            if(znaki.get(i).equals('÷'))
            {
                chisla.set(i,chisla.get(i)/chisla.get(i+1));
                chisla.remove(i+1);
                znaki.remove(i);
                i--;
                vihod--;
            }
            else if(znaki.get(i).equals('×'))
            {
                chisla.set(i,chisla.get(i)*chisla.get(i+1));
                chisla.remove(i+1);
                znaki.remove(i);
                i--;
                vihod--;
            }
            else if(znaki.get(i).equals('%'))
            {
                chisla.set(i,chisla.get(i)%chisla.get(i+1));
                chisla.remove(i+1);
                znaki.remove(i);
                i--;
                vihod--;
            }
        }
        for(int i =vhod; i<vihod;i++)
        {
            if(znaki.get(i).equals('+'))
            {
                chisla.set(i,chisla.get(i)+chisla.get(i+1));
                chisla.remove(i+1);
                znaki.remove(i);
                i--;
                vihod--;
            }
            else if(znaki.get(i).equals('-'))
            {
                chisla.set(i,chisla.get(i)-chisla.get(i+1));
                chisla.remove(i+1);
                znaki.remove(i);
                i--;
                vihod--;
            }
        }
    }

    public void scob2Click(View view)
    {
        if(skobki.size()>0)
        {
            if (chi != "") {
                znaki.add(')');
                chisla.add(Double.parseDouble(chi));
                chi = "";
                TextView output = findViewById(R.id.output);
                output.setText(output.getText() + ")");
                skobki.remove(skobki.size() - 1);
            }
            else if (znaki.get(znaki.size() - 1).equals(')')) {
                znaki.add(')');
                //chisla.add(null);
                TextView output = findViewById(R.id.output);
                output.setText(output.getText() + ")");
                skobki.remove(skobki.size() - 1);
            }
        }
        else
        {
            TextView otlad = findViewById(R.id.otlad);
            otlad.setText("Нет открывающей скобки!");
        }
    }
    public void scob1Click(View view)
    {
        if(chi==""&&(znaki.size()>0||(znaki.size()==0&&chisla.size()==0)))
        {
            znaki.add('(');
            skobki.add('(');
            chisla.add(null);
            TextView output = findViewById(R.id.output);
            output.setText(output.getText()+"(");
        }
    }
    public void stepenClick(View view)
    {
        dobznak('^');
    }
    public void umnClick(View view)
    {
        dobznak('×');
    }
    public void ostClick(View view)
    {
        dobznak('%');
    }
    public void delitClick(View view)
    {
        dobznak('÷');
    }
    public void plusClick(View view)
    {
        dobznak('+');
    }
    public void minusClick(View view)
    {
        if (chi==""&&!znaki.get(znaki.size()-1).equals(')'))
            dobchislo('-');
        else
            dobznak('-');
    }

    public void nolClick(View view) {
        dobchislo('0');
    }
    public void odinClick(View view) {
        dobchislo('1');
    }
    public void dvaClick(View view) {
        dobchislo('2');
    }
    public void triClick(View view) {
        dobchislo('3');
    }
    public void chetireClick(View view) {
        dobchislo('4');
    }
    public void pyatClick(View view) {
        dobchislo('5');
    }
    public void shestClick(View view) {
        dobchislo('6');
    }
    public void semClick(View view) {
        dobchislo('7');
    }
    public void vosemClick(View view) {
        dobchislo('8');
    }
    public void devyatClick(View view) {
        dobchislo('9');
    }
    public void zapClick (View view)
    {
        dobchislo('.');
    }


}
