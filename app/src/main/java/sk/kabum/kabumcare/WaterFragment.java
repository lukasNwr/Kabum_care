package sk.kabum.kabumcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import sk.kabum.kabumcare.Objects.User;
import sk.kabum.kabumcare.dbs.DatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int cup = 100;
    private int water = 0;
    private int maxWatterIntake = 4000;
    private long datecheck;
    private boolean getExp = false;

    private OnFragmentInteractionListener mListener;

    public WaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaterFragment newInstance(String param1, String param2) {
        WaterFragment fragment = new WaterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_water_counter, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        cup = sharedPreferences.getInt("cup",cup);
        datecheck = sharedPreferences.getLong("dateCheck",new Date(System.currentTimeMillis()).getTime());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        if(fmt.format(new Date(datecheck)).equals(fmt.format(new Date(System.currentTimeMillis())))){
            water = sharedPreferences.getInt("watIntake",0);
        }else{
            water = 0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("dateCheck",new Date(System.currentTimeMillis()).getTime());
            editor.putInt("watIntake",0);
            editor.apply();

        }

        TextView waterView = getView().findViewById(R.id.intake);
        waterView.setText(Integer.toString(this.water)+"ml");

        final TextView watterIntake = getView().findViewById(R.id.cupSize);
        watterIntake.setText(Integer.toString(cup)+"ml");

        Button buttonPlus = getView().findViewById(R.id.plus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cup = cup + 50;
                TextView watterIntake = getView().findViewById(R.id.cupSize);
                watterIntake.setText(Integer.toString(cup)+"ml");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("cup",cup);
                editor.apply();
            }
        });

        Button buttonMinus = getView().findViewById(R.id.minus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cup = cup - 50;
                TextView watterIntake = getView().findViewById(R.id.cupSize);
                watterIntake.setText(Integer.toString(cup)+"ml");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("cup",cup);
                editor.apply();
            }
        });

        Button drink = getView().findViewById(R.id.add);
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaterFragment.this.water = WaterFragment.this.water + cup;
                if(WaterFragment.this.water <= 0){
                    WaterFragment.this.water = 0;
                }
                TextView intake = getView().findViewById(R.id.intake);
                intake.setText(Integer.toString(WaterFragment.this.water)+"ml");
                ProgressBar bar = getView().findViewById(R.id.progressWatter);
                bar.setProgress(WaterFragment.this.water);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("watIntake", WaterFragment.this.water);
                editor.putLong("dateCheck",new Date(System.currentTimeMillis()).getTime());
                editor.apply();
                if (water >= maxWatterIntake && !getExp){
                    DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                    RuntimeExceptionDao<User,Integer> dao = databaseHelper.getUserRuntimeDao();
                    List<User> list = dao.queryForAll();
                    User user = list.get(list.size()-1);
                    user.setXp(user.getXp()+10);
                    dao.update(user);
                    ProgressBar expBar = getActivity().findViewById(R.id.progressBarExp);
                    expBar.setProgress(user.getXp());

                    Toast.makeText(getContext(), "Congratulations you have reached your drinking goal! You have earned 10EXP.", Toast.LENGTH_LONG).show();
                    getExp = true;
                }
            }
        });

        maxWatterIntake = sharedPreferences.getInt("watterIntake",maxWatterIntake);

        if (water >= maxWatterIntake){
            getExp = true;
        }

        ProgressBar bar = getView().findViewById(R.id.progressWatter);
        bar.setMax(maxWatterIntake);
        bar.setProgress(this.water);
        TextView maxWatter = getView().findViewById(R.id.maxWatter);
        maxWatter.setText(Integer.toString(maxWatterIntake));

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Set your goal in ml.");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        maxWatterIntake = Integer.parseInt(input.getText().toString());
                        TextView maxStepsTV = getView().findViewById(R.id.maxWatter);
                        maxStepsTV.setText(Integer.toString(maxWatterIntake));
                        ProgressBar bar1 = getView().findViewById(R.id.progressWatter);
                        bar1.setMax(maxWatterIntake);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("watterIntake",maxWatterIntake);
                        editor.apply();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.show();

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
