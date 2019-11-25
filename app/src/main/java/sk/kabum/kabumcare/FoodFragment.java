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
import android.util.Log;
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
 * {@link FoodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int calories = 0;
    private int maxCalories = 3000;
    private long datecheck;
    private boolean getExp = false;

    private OnFragmentInteractionListener mListener;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
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
        return inflater.inflate(R.layout.activity_food, container, false);
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
        maxCalories = sharedPreferences.getInt("cal",maxCalories);
        datecheck = sharedPreferences.getLong("dateCheck",new Date(System.currentTimeMillis()).getTime());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        if(fmt.format(new Date(datecheck)).equals(fmt.format(new Date(System.currentTimeMillis())))){
            calories = sharedPreferences.getInt("calIntake",0);
        }
        else{
            calories = 0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("dateCheck",new Date(System.currentTimeMillis()).getTime());
            editor.putInt("calIntake",0);
            editor.apply();

        }
        if(calories >= maxCalories){
            getExp = true;
        }
        TextView calIntake = getView().findViewById(R.id.maxCal);
        calIntake.setText(Integer.toString(maxCalories)+"cal");

        TextView cal = getView().findViewById(R.id.calories);
        cal.setText(Integer.toString(calories)+"cal");

        ProgressBar bar = getView().findViewById(R.id.progressCal);
        bar.setMax(maxCalories);
        bar.setProgress(calories);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Set your calories goal in cal.");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        maxCalories = Integer.parseInt(input.getText().toString());
                        TextView maxStepsTV = getView().findViewById(R.id.maxCal);
                        maxStepsTV.setText(Integer.toString(maxCalories));
                        ProgressBar bar1 = getView().findViewById(R.id.progressCal);
                        bar1.setMax(maxCalories);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("cal",maxCalories);
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

        Button button = getView().findViewById(R.id.addCal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Add calories in cal.");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        calories =  calories + Integer.parseInt(input.getText().toString());
                        ProgressBar bar1 = getView().findViewById(R.id.progressCal);
                        bar1.setProgress(calories);
                        TextView view1 = getView().findViewById(R.id.calories);
                        view1.setText(Integer.toString(calories)+"cal");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("calIntake",calories);
                        editor.putLong("dateCheck",new Date(System.currentTimeMillis()).getTime());
                        editor.apply();
                        if (calories >= maxCalories && !getExp){
                            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                            RuntimeExceptionDao<User,Integer> dao = databaseHelper.getUserRuntimeDao();
                            List<User> list = dao.queryForAll();
                            User user = list.get(list.size()-1);
                            user.setXp(user.getXp()+10);
                            dao.update(user);
                            ProgressBar expBar = getActivity().findViewById(R.id.progressBarExp);
                            expBar.setProgress(user.getXp());
                            Toast.makeText(getContext(), "COngratulations you have reach your calories goal! You have earn 10EXP.", Toast.LENGTH_LONG).show();
                            getExp = true;
                        }

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
