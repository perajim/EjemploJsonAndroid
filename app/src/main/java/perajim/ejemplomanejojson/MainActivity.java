package perajim.ejemplomanejojson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button crear, leer;
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crear = (Button) findViewById(R.id.crear);
        leer= (Button) findViewById(R.id.leer);
        texto=(TextView) findViewById(R.id.texto);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createJson();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    readJson();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void createJson() throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject item;
        item = new JSONObject();
        item.put("nombre", "plato");
        item.put("cantidad", 10);
        jsonArray.put(item);
        item = new JSONObject();
        item.put("nombre", "computadora");
        item.put("cantidad", 1);
        jsonArray.put(item);
        item = new JSONObject();
        item.put("nombre", "mesa");
        item.put("cantidad", 100);
        jsonArray.put(item);

        String text =  jsonArray.toString();
        FileOutputStream fos= openFileOutput("items", MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();
        texto.setText(jsonArray.toString());

    }

    public void readJson() throws IOException, JSONException {
        FileInputStream fis = openFileInput("items");
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer stringBuffer = new StringBuffer();

        while(bis.available() != 0){
            char c = (char) bis.read();
            stringBuffer.append(c);
        }
        bis.close();
        fis.close();
        JSONArray jsonArray = new JSONArray(stringBuffer.toString());

        StringBuffer itemsBuffer = new StringBuffer();
        for (int i = 0; i < jsonArray.length() ; i++){
            String item = jsonArray.getJSONObject(i).getString("nombre");
            itemsBuffer.append(item + "\n");
            texto.setText(itemsBuffer.toString());
        }
    }
}
