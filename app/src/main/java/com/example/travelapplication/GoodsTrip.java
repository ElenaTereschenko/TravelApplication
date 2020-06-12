
package com.example.travelapplication;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckedTextView;
        import android.widget.EditText;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

        import static java.lang.Integer.parseInt;

public class GoodsTrip extends AppCompatActivity {
    public List<Good> goods;

    private String id;
    private String userId;
    private String name;
    private String description;
    private boolean isTook;
    private int count;


    private SharedPreferences preferences;
    private String token;

    private RecyclerView listOfGoods;
    private GoodsTripAdapter listOfGoodsAdapter;
    private Button doneButton;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_trip);

        Bundle arguments = getIntent().getExtras();
        final Trip trip;
        if (arguments != null){
            trip = arguments.getParcelable("trip");
            goods = new ArrayList<>();

            try {
                if (trip.getGoodsId().size() > 0) {

                    String[] params = new String[trip.getGoodsId().size()];
                    params = trip.getGoodsId().toArray(params);
                    new GoodsTrip.SendGetRead().execute(params).get();

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            trip = new Trip();
        }

        doneButton = findViewById(R.id.button_goodsTrip_done);
        fab = findViewById(R.id.fab_goodsTrip);

        listOfGoods = findViewById(R.id.recycleview_goodsTrip_goodsTrip);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfGoods.setLayoutManager(layoutManager);

        listOfGoods.setHasFixedSize(true);
        listOfGoodsAdapter = new GoodsTripAdapter(goods,true);
        listOfGoods.setAdapter(listOfGoodsAdapter);

        final Context context = getBaseContext();
        /*
        listOfGoods.addOnItemTouchListener(
                new RecyclerItemClickListener(getBaseContext(), listOfGoods, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        goods.get(position).setTook(!goods.get(position).isTook());
                        listOfGoodsAdapter .updateDate(goods);
                        listOfGoodsAdapter .notifyDataSetChanged();

                        String[] params = new String[1];
                        params [0] = "" + position;
                        try {
                            new GoodsTrip.SendPostUpsert().execute(params).get();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        listOfGoodsAdapter.changeVisibility(false);
                        listOfGoodsAdapter.notifyDataSetChanged();
                        doneButton.setVisibility(View.VISIBLE);

                    }
                })
        );*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GoodsTrip.this,AddingGood.class);
                intent.putExtra("trip", trip);
                startActivity(intent);
            }
        });
    }

    public class SendGetRead extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                token = preferences.getString("token", "");

                for (String id : ids){
                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/good/read" + "?id=" + id + "&token=" + token);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(15000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("GET");

                    connection.setDoInput(true);

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK ) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line = "";

                        while ((line = in.readLine()) != null) {
                            sb.append(line);
                            break;
                        }

                        in.close();

                        //Парсим JSON
                        Good good = doGoodFromJSON(sb.toString(), id);

                        if(good!=null){
                            goods.add(good);
                        }

                    } else {
                        return new String("false : " + responseCode);
                    }
                }



            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}

    public class SendPostUpsert extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... ids) {
            try {

                //Получаем токен
                preferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                token = preferences.getString("token", "");

                for (String id : ids){
                    Good good = goods.get(parseInt(id));
                    //Формируем запрос
                    URL url = new URL("http://travelapp.fun/api/good/upsert" + "?token=" + token);

                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("Id", good.getId());
                    postDataParams.put("IsTook",good.isTook());
                    postDataParams.put("UserId",good.getUserId());
                    postDataParams.put("Name",good.getName());
                    postDataParams.put("Description",good.getDescription());
                    postDataParams.put("Count", good.getCount());
                    Log.e("params",postDataParams.toString());

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(15000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type","application/json");
                    connection.setDoOutput(true);

                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    writer.write(postDataParams.toString());

                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = connection.getResponseCode();



                    if (responseCode == HttpURLConnection.HTTP_OK ) {

                        break;

                    } else {
                        return new String("false : " + responseCode);
                    }
                }



            } catch (Exception ex) {
                return new String("Exception: " + ex.getMessage());
            }

            return "";
        }}


    private Good doGoodFromJSON(String resultString,String id ){
        try {
            JSONObject result = new JSONObject(resultString);

            userId = result.getString("userId");
            name = result.getString("name");
            description = result.getString("description");
            isTook = result.getBoolean("isTook");
            count = result.getInt("count");


                count = 2;



            //Добавляем вещь

            Good good = new Good (id, userId, name, description, count, isTook);

            return good;
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}

