public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        //User currentUser = Settings.getCurrentUser();
        Settings.setLoggedInUser(new User());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Class startIntent = Settings.getLoggedInUser() == null ? LoginActivity.class : NavigationActivity.class;
        startActivity(new Intent(this, startIntent));
    }
}