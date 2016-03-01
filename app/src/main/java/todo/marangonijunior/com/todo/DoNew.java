package todo.marangonijunior.com.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import controller.ToDOController;
import entidade.ToDO;

/**
  * Created by hedneijr on 29/01/2016.
  */
public class DoNew extends ActionBarActivity {
    private CursorAdapter dataSource;
    private ToDOController dataSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donew);

        Spinner spinnerList = (Spinner) findViewById(R.id.spinnerList);
        List<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Media");
        list.add("Urgente");
        list.add("Mega Urgente");
        list.add("Concluida");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinnerList.setAdapter(dataAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donew, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            ToDOController iDataBase = new ToDOController(getBaseContext());
            ToDO todo = new ToDO();

            EditText Titulo = (EditText) findViewById(R.id.editText1);
            String tituloT = (String) Titulo.getText().toString() ;

            EditText Detalhe = (EditText) findViewById(R.id.editText2);
            String detalheT = (String) Detalhe.getText().toString() ;

            Spinner spinnerList = (Spinner) findViewById(R.id.spinnerList);
            String spinnerListT = String.valueOf(spinnerList.getSelectedItem());

            todo.setTitulo(tituloT);
            todo.setDetalhe(detalheT);
            todo.setUrgencia(spinnerListT);

            iDataBase.insertToDo(todo);

            Intent it = new Intent(this, HomeToDO.class);
            startActivityForResult(it, 0);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            Intent it = new Intent(this, HomeToDO.class);
            startActivityForResult(it, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
