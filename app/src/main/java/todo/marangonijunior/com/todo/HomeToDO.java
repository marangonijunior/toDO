package todo.marangonijunior.com.todo;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.GPSTracker;
import controller.Getnet;
import controller.ToDOController;
import entidade.ToDO;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;


public class HomeToDO extends ActionBarActivity {
    private CursorAdapter dataSource;
    private ToDOController toDoCont = new ToDOController(this);
    final int TAKE_PICTURE = 1;
    private Uri imageUri;
    GPSTracker gps;
    Getnet mNet = new Getnet(this);
    public List<ToDO> result = new ArrayList<ToDO>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometodo);

        ListView listView = (ListView) findViewById(R.id.listView1);
        result = toDoCont.listToDO();
        toDOAdapter adapter = new toDOAdapter(getApplicationContext(),R.layout.task,result);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDO tarefa = new ToDO();
                tarefa.setDetalhe(result.get(i).getDetalhe());
                //Toast
                //makeText(getApplicationContext(), tarefa.getDetalhe(), LENGTH_LONG).show();
                String detalheSet = tarefa.getDetalhe();
                openDialog(detalheSet);
            }
        });



    }

    public void openDialog(String detalheOk) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Detalhes ToDO");

        TextView text = (TextView) dialog.findViewById(R.id.dialog_info);
        text.setText(detalheOk);

        dialog.show();

        Button dialogButton = (Button) dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }



    public class toDOAdapter extends ArrayAdapter{

        private  List<ToDO> toDOList;
        private int resource;
        private LayoutInflater inflater;
        public toDOAdapter(Context context, int resource, List<ToDO> objects) {
            super(context, resource, objects);
            toDOList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = inflater.inflate(resource, null);
            }

            ImageView imgTask;
            TextView idTitulo;
            TextView idUrgencia;

            imgTask = (ImageView)convertView.findViewById(R.id.imgTask);
            idTitulo = (TextView)convertView.findViewById(R.id.idTitulo);
            idUrgencia = (TextView)convertView.findViewById(R.id.idUrgencia);

            idTitulo.setText(toDOList.get(position).getTitulo());
            idUrgencia.setText(toDOList.get(position).getUrgencia());

            return convertView;
        }
    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(),  "FotoTesteApp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void fgetNet(){
        boolean mResultado = mNet.getInternet();
        if(mResultado){
            Toast.makeText(this, "Online", LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Offline", LENGTH_SHORT).show();
        }
    }

    public void getJson(){

        Toast.makeText(this, "Breve", LENGTH_SHORT).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.IMAGE);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this, "A foto :) " + selectedImage.toString(),
                                 LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Falhou :(", LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hometodo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {
            Intent it = new Intent(this, DoNew.class);
            startActivityForResult(it, 0);
            return true;
        }
        if (id == R.id.action_photo) {
            takePhoto();
            return true;
        }
        if (id == R.id.action_gps){
            gps = new GPSTracker(HomeToDO.this);
            if(gps.canGetLocation()){

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Toast.makeText(getApplicationContext(), "Sua localizacao - \nLat: " + latitude + "\nLong: " + longitude, LENGTH_LONG).show();
            }else{
                gps.showSettingsAlert();
            }
            return true;
        }
        if (id == R.id.action_json) {

        }
        if (id == R.id.action_net) {
            fgetNet();
        }


        return super.onOptionsItemSelected(item);
    }
}
