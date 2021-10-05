package com.tonic.internalapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.tonic.internalapp.data.Constants
import com.tonic.internalapp.databinding.ActivityMainBinding
import com.tonic.internalapp.ui.announcement.AnnouncementFragment
import com.tonic.internalapp.ui.home.HomeFragment

//class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
class MainActivity : AppCompatActivity() {
    private val mTAG = MainActivity::class.java.name
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val requestIdMultiplePermission = 1

    enum class CurrentFragment {
        HOME_FRAGMENT, INDICATOR_FRAGMENT, ME_FRAGMENT, ANNOUNCEMENT_FRAGMENT, EDIT_FRAGMENT, BALANCE_FRAGMENT
    }

    var currentFrag: CurrentFragment = CurrentFragment.HOME_FRAGMENT



    private var mContext: Context? = null



    private var mReceiver: BroadcastReceiver? = null
    private var isRegister = false

    companion object {
        @JvmStatic var screenWidth: Int = 0
        @JvmStatic var screenHeight: Int = 0
        @JvmStatic var isKeyBoardShow: Boolean = false
        @JvmStatic var webViewProgressBar: ProgressBar? = null
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        Log.d(mTAG, "onCreate")

        mContext = applicationContext

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            )
        )*/

        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //val toolbar: Toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)


        /*
        val displayMetrics = DisplayMetrics()

        //
        //mContext!!.display!!.getMetrics(displayMetrics)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
        {
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            screenHeight = displayMetrics.heightPixels
            screenWidth = displayMetrics.widthPixels
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            mContext!!.display!!.getRealMetrics(displayMetrics)

            screenHeight = displayMetrics.heightPixels
            screenWidth = displayMetrics.widthPixels
        } else { //Android 11
            //mContext!!.display!!.getMetrics(displayMetrics)
            screenHeight = windowManager.currentWindowMetrics.bounds.height()
            screenWidth = windowManager.currentWindowMetrics.bounds.width()

        }

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        //val drawerLayout: DrawerLayout = binding.drawerLayout
        //navView: NavigationView = binding.navView
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        Log.e(mTAG, "navView header: "+navView!!.headerCount)
        val header = navView!!.inflateHeaderView(R.layout.nav_header_main)
        navView!!.removeHeaderView(navView!!.getHeaderView(0))


        val mDrawerToggle = object : ActionBarDrawerToggle(
            this, /* host Activity */
            drawerLayout, /* DrawerLayout object */
            toolbar, /* nav drawer icon to replace 'Up' caret */
            R.string.navigation_drawer_open, /* "open drawer" description */
            R.string.navigation_drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state.  */

            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)

                Log.d(mTAG, "onDrawerClosed")

            }

            /** Called when a drawer has settled in a completely open state.  */
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                Log.d(mTAG, "onDrawerOpened")

                if (isKeyBoardShow) {
                    imm?.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0)
                }
            }
        }

        drawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        navView!!.setNavigationItemSelectedListener(this)
        */


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions()
        } else {
            //initView()
            //if (isLogEnable)
            //    initLog()
        }*/

        val iidToken = FirebaseInstallations.getInstance().getToken(true)
        Log.d(mTAG, "IID_TOKEN = $iidToken")



        FirebaseMessaging.getInstance().subscribeToTopic("test")


        val filter: IntentFilter
        @SuppressLint("CommitPrefEdits")
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action != null) {
                    if (intent.action!!.equals(Constants.ACTION.ACTION_LOGIN_ACTION, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_LOGIN_ACTION")

                    } else if (intent.action!!.equals(Constants.ACTION.ACTION_LOGIN_NETWORK_ERROR, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_LOGIN_NETWORK_ERROR")
                    } else if (intent.action!!.equals(Constants.ACTION.ACTION_LOGIN_FAILED, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_LOGIN_FAILED")
                    } else if (intent.action!!.equals(Constants.ACTION.ACTION_LOGIN_SUCCESS, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_LOGIN_SUCCESS")
                    } else if (intent.action!!.equals(Constants.ACTION.ACTION_HOME_GO_TO_ANNOUNCEMENT_ACTION, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_HOME_GO_TO_ANNOUNCEMENT_ACTION")

                        navController.navigate(R.id.nav_announcement)
                    } else if (intent.action!!.equals(Constants.ACTION.ACTION_HOME_GO_TO_EDIT_ACTION, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_HOME_GO_TO_EDIT_ACTION")

                        navController.navigate(R.id.nav_edit)
                    } else if (intent.action!!.equals(Constants.ACTION.ACTION_HOME_GO_TO_BALANCE_ACTION, ignoreCase = true)) {
                        Log.d(mTAG, "ACTION_HOME_GO_TO_BALANCE_ACTION")

                        navController.navigate(R.id.nav_balance)
                    }
                }
            }
        }

        if (!isRegister) {
            filter = IntentFilter()
            //login
            filter.addAction(Constants.ACTION.ACTION_LOGIN_ACTION)
            filter.addAction(Constants.ACTION.ACTION_LOGIN_NETWORK_ERROR)
            filter.addAction(Constants.ACTION.ACTION_LOGIN_SUCCESS)
            filter.addAction(Constants.ACTION.ACTION_LOGIN_FAILED)
            //home
            filter.addAction(Constants.ACTION.ACTION_HOME_GO_TO_ANNOUNCEMENT_ACTION)
            filter.addAction(Constants.ACTION.ACTION_HOME_GO_TO_EDIT_ACTION)
            filter.addAction(Constants.ACTION.ACTION_HOME_GO_TO_BALANCE_ACTION)

            mContext!!.registerReceiver(mReceiver, filter)
            isRegister = true
            Log.d(mTAG, "registerReceiver mReceiver")
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/

    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectDrawerItem(item)

        return true
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        var fragment: Fragment? = null
        var fragmentClass: Class<*>? = null

        var title = ""
        //hide keyboard
        val view = currentFocus

        if (view != null) {
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        navView!!.menu.getItem(0).isChecked = false //home
        navView!!.menu.getItem(1).isChecked = false //gallery
        navView!!.menu.getItem(2).isChecked = false //slideshow




        currentSelectMenuItem = menuItem

        when (menuItem.itemId) {
            R.id.nav_home -> {
                title = getString(R.string.menu_home)
                fragmentClass = HomeFragment::class.java
                menuItem.isChecked = true
                currentFrag = CurrentFragment.HOME_FRAGMENT
            }
            R.id.nav_gallery -> {

                title = getString(R.string.menu_gallery)
                fragmentClass = GalleryFragment::class.java
                menuItem.isChecked = true
                currentFrag = CurrentFragment.GALLERY_FRAGMENT

            }

            R.id.nav_slideshow -> {

                title = getString(R.string.menu_slideshow)
                fragmentClass = SlideshowFragment::class.java
                menuItem.isChecked = true
                currentFrag = CurrentFragment.SLIDESHOW_FRAGMENT


            }

        }

        if (fragmentClass != null) {
            try {
                fragment = fragmentClass.newInstance() as Fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }


            // Insert the fragment by replacing any existing fragment
            val fragmentManager = supportFragmentManager
            //fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment!!).commitAllowingStateLoss()

            // Highlight the selected item has been done by NavigationView

            // Set action bar title
            if (title.isNotEmpty())
                setTitle(title)
            else
                setTitle(menuItem.title)

            // Close the navigation drawer
            val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
            drawer.closeDrawer(GravityCompat.START)
        }



    }*/

    private fun checkAndRequestPermissions() {

        //int accessNetworkStatePermission = ContextCompat.checkSelfPermission(this,
        //        Manifest.permission.ACCESS_NETWORK_STATE);

        //int accessWiFiStatePermission = ContextCompat.checkSelfPermission(this,
        //        Manifest.permission.ACCESS_WIFI_STATE);

        /*val readPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        var manageExternalPermission = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            manageExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }*/



        val networkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)

        /*val coarsePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        val bluetoothAdminPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN)

        val bluetoothPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)

        val accessNetworkStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)

        val accessWiFiStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)

        val changeWifiStatePermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE)*/
        //int cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);

        val listPermissionsNeeded = ArrayList<String>()

        /*if (readPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (manageExternalPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            }
        }*/

        if (networkPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET)
        }

        /*if (coarsePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (bluetoothAdminPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN)
        }

        if (bluetoothPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.BLUETOOTH)
        }

        if (accessNetworkStatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE)
        }

        if (accessWiFiStatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE)
        }

        if (changeWifiStatePermissions != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CHANGE_WIFI_STATE)
        }*/
        //if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
        //    listPermissionsNeeded.add(android.Manifest.permission.WRITE_CALENDAR);
        //}
        //if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
        //    listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        //}

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                requestIdMultiplePermission
            )
            //return false;
        } else {
            Log.e(mTAG, "All permission are granted")
            //initView()
            //initLog()
        }
        //return true;
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(mTAG, "Permission callback called------- permissions.size = ${permissions.size}")
        when (requestCode) {
            requestIdMultiplePermission -> {

                val perms: HashMap<String, Int> = HashMap()

                // Initialize the map with both permissions
                //perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                //perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                //    perms[Manifest.permission.MANAGE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                //}

                perms[Manifest.permission.INTERNET] = PackageManager.PERMISSION_GRANTED
                //perms[Manifest.permission.ACCESS_COARSE_LOCATION] = PackageManager.PERMISSION_GRANTED
                //perms[Manifest.permission.BLUETOOTH_ADMIN] = PackageManager.PERMISSION_GRANTED
                //perms[Manifest.permission.BLUETOOTH] = PackageManager.PERMISSION_GRANTED
                //perms[Manifest.permission.ACCESS_NETWORK_STATE] = PackageManager.PERMISSION_GRANTED
                //perms[Manifest.permission.ACCESS_WIFI_STATE] = PackageManager.PERMISSION_GRANTED
                //perms[Manifest.permission.CHANGE_WIFI_STATE] = PackageManager.PERMISSION_GRANTED
                //perms.put(Manifest.permission.ACCESS_WIFI_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                //if (grantResults.size > 0) {
                if (grantResults.isNotEmpty()) {
                    for (i in permissions.indices) {
                        perms[permissions[i]] = grantResults[i]
                        Log.e(mTAG, "perms[permissions[$i]] = ${permissions[i]}")

                    }
                    // Check for both permissions
                    if (
                        //perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                        //&& perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                        perms[Manifest.permission.INTERNET] == PackageManager.PERMISSION_GRANTED
                        //&& perms[Manifest.permission.ACCESS_COARSE_LOCATION] == PackageManager.PERMISSION_GRANTED
                        //&& perms[Manifest.permission.BLUETOOTH_ADMIN] == PackageManager.PERMISSION_GRANTED
                        //&& perms[Manifest.permission.BLUETOOTH] == PackageManager.PERMISSION_GRANTED
                        //&& perms[Manifest.permission.ACCESS_NETWORK_STATE] == PackageManager.PERMISSION_GRANTED
                        //&& perms[Manifest.permission.ACCESS_WIFI_STATE] == PackageManager.PERMISSION_GRANTED
                        //&& perms[Manifest.permission.CHANGE_WIFI_STATE] == PackageManager.PERMISSION_GRANTED
                    ) {
                        //Log.d(mTAG, "write permission granted")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            /*if (perms[Manifest.permission.MANAGE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED) {
                                Log.e(mTAG, "MANAGE_EXTERNAL_STORAGE is permmited.")

                            } else {
                                Log.e(mTAG, "MANAGE_EXTERNAL_STORAGE not permmited.")
                            }*/
                            //initView()
                            //initLog()
                        } else {
                            //initView()
                            //initLog()
                        }
                        // process the normal flow
                        //else any one or both the permissions are not granted
                        //init_folder_and_files()
                        //init_setting();

                    } else {
                        Log.d(mTAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                            ||
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE
                            )
                            ||
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.INTERNET
                            )
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.BLUETOOTH_ADMIN
                            )
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.BLUETOOTH
                            )
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_NETWORK_STATE
                            )
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_WIFI_STATE
                            )
                            || ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.CHANGE_WIFI_STATE
                            )
                        ) {
                            showDialogOK { _, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                    DialogInterface.BUTTON_NEGATIVE ->
                                        // proceed with logic by disabling the related features or quit the app.
                                        finish()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                .show()
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }//|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE )
                        //|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE )
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                    }//&& perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                    //perms.get(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
                }
            }
        }

    }

    private fun showDialogOK(okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage("Warning")
            .setPositiveButton("Ok", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    /*private fun initView() {



        //set title
        title = getString(R.string.menu_home)

        //show menu


        navView!!.menu.getItem(0).isVisible = true //home
        navView!!.menu.getItem(1).isVisible = true //gallery
        navView!!.menu.getItem(2).isVisible = true //slideshow




        var fragment: Fragment? = null
        val fragmentClass: Class<*>
        fragmentClass = HomeFragment::class.java

        try {
            fragment = fragmentClass.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fragmentManager = supportFragmentManager
        //fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment!!).commitAllowingStateLoss()

        navView!!.menu.getItem(0).isChecked = true //home
        navView!!.menu.getItem(1).isChecked = false //gallery
        navView!!.menu.getItem(2).isChecked = false //slideshow
    }*/
}