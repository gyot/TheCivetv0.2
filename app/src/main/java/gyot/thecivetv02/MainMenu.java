package gyot.thecivetv02;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import gyot.thecivetv02.RestAPI.RestAPI;
import gyot.thecivetv02.model.Models;
import gyot.thecivetv02.model.order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    private final String baseURL = "http://192.168.61.1/data_api/";
//

    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    private List<String> id = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> price = new ArrayList<>();
    private List<String> imgSrc = new ArrayList<>();
    private boolean aBoolean = true;
    private int index = -1;
    private String getmenu = "food";
    private String user = null;
    private String table = null;
    private GoogleMap googleMap;
    private double latitude;
    private double longtitude;
    private double cvtlat = -7.7902776;
    private double cvtlang = 110.3640668;
    private boolean posisi=false;
    private int id_penjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout2 = (LinearLayout) findViewById(R.id.cart);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                lokasi();
//                Toast.makeText(MainMenu.this, latitude+","+longtitude, Toast.LENGTH_LONG).show();
                // formattedDate have current date/time
//                Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainMenu.this, id_penjualan+"", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getMenu("2");
//        Intent intent = new Intent(MainMenu.this, Maps.class);
//        startActivity(intent);
    }

    private void getMenu(final String category) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestAPI restAPI = retrofit.create(RestAPI.class);
        Call<List<Models>> call = restAPI.getMenu();
        call.enqueue(new Callback<List<Models>>() {
            @Override
            public void onResponse(Call<List<Models>> call, Response<List<Models>> response) {
                final List<Models> modelses = response.body();
                linearLayout = (LinearLayout) findViewById(R.id.items);
                linearLayout.removeAllViews();
                for (int i = 0; i < modelses.size(); i++) {
                    if (modelses.get(i).getId_katagori().equals(category)){
                        final CardView cardView = new CardView(MainMenu.this);
                        cardView.setLayoutParams(new ViewGroup.LayoutParams(percentW(90), percentH(65)));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        params.setMargins(0, 0, dp(10), 0);
                        cardView.setLayoutParams(params);

                        LinearLayout layout1 = new LinearLayout(MainMenu.this);
                        layout1.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout layout2 = new LinearLayout(MainMenu.this);
                        layout2.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout layout3 = new LinearLayout(MainMenu.this);
                        layout3.setOrientation(LinearLayout.HORIZONTAL);

                        TextView tName = new TextView(MainMenu.this);
                        tName.setText(modelses.get(i).getNm_menu());
                        tName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        tName.setTextSize(dp(20));
                        tName.setPadding(dp(10), dp(0), dp(0), dp(0));
                        tName.setTextColor(0xff8d2906);
//            tNa

                        TextView tPrice = new TextView(MainMenu.this);
                        tPrice.setText("Rp " + modelses.get(i).getHarga());
                        tPrice.setPadding(dp(10), dp(0), dp(40), dp(0));
                        tPrice.setTextSize(dp(16));
                        tPrice.setTextColor(0xff8d2906);

                        ImageView iImg = new ImageView(MainMenu.this);
                        iImg.setLayoutParams(new ViewGroup.LayoutParams(percentW(90), percentH(50)));
                        Picasso.with(MainMenu.this).load(baseURL +"menu/" +modelses.get(i).getImg()).into(iImg);

                        final Button chkOrder = new Button(MainMenu.this);
                        chkOrder.setLayoutParams(new ViewGroup.LayoutParams(percentW(30), percentH(8)));
                        chkOrder.setTextSize(dp(12));
                        chkOrder.setText("Order");
                        chkOrder.setPadding(0, 0, 0, 0);
                        chkOrder.setBackgroundColor(0xff8d2906);
                        chkOrder.setTextColor(0xffffffff);
                        final int finalI3 = i;
                        chkOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imgSrc.add(baseURL +"menu/"+ modelses.get(finalI3).getImg());
                                id.add(modelses.get(finalI3).getId_menu());
                                name.add(modelses.get(finalI3).getNm_menu());
                                price.add(modelses.get(finalI3).getHarga());
                                getCart();
                            }
                        });

                        layout2.addView(tName);
                        layout2.addView(iImg);

                        layout3.addView(tPrice);
                        layout3.addView(chkOrder);

                        layout1.addView(layout2);
                        layout1.addView(layout3);

                        cardView.addView(layout1);
                        linearLayout.addView(cardView);
                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Models>> call, Throwable t) {

            }
        });
    }

    private void getCart() {
        linearLayout2.removeAllViews();
        for (int j = 0; j < imgSrc.size(); j++) {
            ImageView cartImg = new ImageView(MainMenu.this);
            cartImg.setLayoutParams(new LinearLayout.LayoutParams(percentW(20), percentH(10)));
            Picasso.with(MainMenu.this).load(imgSrc.get(j)).into(cartImg);
            final int finalJ = j;
            cartImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgSrc.remove(finalJ);
                    id.remove(finalJ);
                    name.remove(finalJ);
                    price.remove(finalJ);
                    getCart();
                }
            });
            linearLayout2.addView(cartImg);
        }
    }

    private void check() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainMenu.this);
        if (id.size() == 0) {
            dialog.setMessage("You have not order anything")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.setCancelable(true);
                        }
                    })
                    .show();
        } else if (id.size() > 0) {
            ScrollView layout = new ScrollView(MainMenu.this);
            TextView textView = new TextView(MainMenu.this);
            int tot = 0;
            textView.setText("Name     Price\n");
            for (int i = 0; i < id.size(); i++) {
                textView.setText(textView.getText() + name.get(i) + " " + price.get(i) + "\n");
                tot = tot + Integer.parseInt(price.get(i));
            }
            textView.setText(textView.getText() + "\nTotal = " + tot);
            layout.addView(textView);
            dialog.setTitle("Your Orders :")
                    .setView(layout)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            sendOrder();
                            LinearLayout layout = new LinearLayout(MainMenu.this);
                            final EditText edtUser = new EditText(MainMenu.this);
                            final EditText edtTable = new EditText(MainMenu.this);
                            layout.setOrientation(LinearLayout.VERTICAL);
                            edtUser.setHint("Insert Your Name");
                            edtTable.setHint("Insert Your Table Number");
                            edtTable.setWidth(MATCH_PARENT);
                            edtUser.setWidth(MATCH_PARENT);
                            layout.addView(edtUser);
                            layout.addView(edtTable);
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                            builder.setView(layout)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            cekLokasi();
                                            if (posisi == true) {
                                                user = edtUser.getText().toString();
                                                table = edtTable.getText().toString();
                                                for (int j = 0; j < id.size(); j++) {
                                                    sendOrder(id.get(j), price.get(j),id_penjualan);
                                                }
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            builder.setCancelable(true);
                                        }
                                    })
                                    .show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.setCancelable(true);
                        }
                    })
                    .show();
        }
    }

    private void cekLokasi() {
        lokasi();
        if (latitude != 0 && longtitude != 0) {
            if (latitude != cvtlat && longtitude != cvtlang) {
                Toast.makeText(this, "Maaf, anda harus berada di Civet Cafe untuk bisa mengirim pesanan anda", Toast.LENGTH_LONG).show();
                Toast.makeText(this,"Lokasi anda adalah : "+latitude+","+longtitude,Toast.LENGTH_LONG);
                posisi=false;
            }else{
                Toast.makeText(this, "Lokasi benar", Toast.LENGTH_LONG).show();
                posisi=true;
            }
        }
    }

    private void lokasi() {
        try {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                builder.setTitle("Info")
                        .setMessage("Please turn on the GPS")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .show();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            try {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                if (location!=null){
                    onLocationChanged(location);
                }
                locationManager.requestLocationUpdates(provider,5000,0, this);
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longtitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void sendOrder(String id, String price, int id_penjualan) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestAPI restAPI = retrofit.create(RestAPI.class);
        Call<order> orderCall = restAPI.ORDER_CALL(Integer.parseInt(id), Integer.parseInt(price), 1, user, table);
        orderCall.enqueue(new Callback<order>() {
            @Override
            public void onResponse(Call<order> call, Response<order> response) {

            }

            @Override
            public void onFailure(Call<order> call, Throwable t) {

            }
        });
    }

    public int percentH(double d) {
        //get resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return (int) ((d / 100) * height);
    }

    public double percentU(double x, double y) {
        double tot = (x / 100) * y;
        return tot;
    }

    public int percentW(double d) {
        //get resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return (int) ((d / 100) * width);
    }

    public int dp(int size) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            getmenu = "food";
            getMenu("2");
        } else if (id == R.id.nav_gallery) {
//            getmenu = "drink";
//            getMenu("1");
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
