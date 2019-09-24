

using Android.Content;
using Android.App;
using Android.Graphics.Drawables;
using Android.OS;
using Android.Runtime;
using Android.Support.V7.App;
using Android.Views;
using Android.Widget;
using Java.Lang;
using System;
using static Android.App.ActionBar;

namespace TrialApp
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.activity_main);
            Button b1 = FindViewById<Button>(Resource.Id.button1);
            b1.Click += Button_Click;
        }
        public override void OnRequestPermissionsResult(int requestCode, string[] permissions, [GeneratedEnum] Android.Content.PM.Permission[] grantResults)
        {
            Xamarin.Essentials.Platform.OnRequestPermissionsResult(requestCode, permissions, grantResults);

            base.OnRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        private void Button_Click(object sender, EventArgs e)
        {

            SetContentView(Resource.Layout.Page1);
            Button b2 = FindViewById<Button>(Resource.Id.Page1Next);
            b2.Click += Button_Click2;
        }
        private void Button_Click2(object sender, EventArgs e)
        {
            SetContentView(Resource.Layout.Page2);
            LayoutInflater layoutInflater = (LayoutInflater) this.GetSystemService(Context.LayoutInflaterService);
            View popUpView = layoutInflater.Inflate(Resource.Layout.PopUp, null);
            var popUp = new PopupWindow(popUpView, 10, 10);
            popUp.ShowAtLocation(FindViewById(Resource.Layout.Page2), GravityFlags.Center, 0, 0);

            //LayoutInflater layoutInflater = (LayoutInflater)BaseContext.GetSystemService(LayoutInflaterService);
            //View popupView = layoutInflater.Inflate(Resource.Layout.PopUpLayout, null);
            //PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WrapContent, LayoutParams.WrapContent);


            ////********* Code you need *******************************

            //popupWindow.SetBackgroundDrawable(new BitmapDrawable());
            //popupWindow.OutsideTouchable = true;

            ////********************************************************


            //Button btnDismiss = (Button)popupView.FindViewById(Resource.Id.dismiss);
            //btnDismiss.Click += delegate {
            //    popupWindow.Dismiss();
            //};

            //popupWindow.ShowAsDropDown(btnOpenPopUp, 50, -30);
        }
    }

}