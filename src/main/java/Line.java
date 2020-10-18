//// Code Objectives are:
//// - Get list of user and show on the screen user's name and profile picture
//public class MainActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // get Users task is long running task, so should run it in background thread.
//        // This is a business logic so we should seperate it out from the Activity. We can create a ViewModel and put all business logic in there.
//        // The Activity should observe to the view model to update the data on UI.
//        List<Pair<String, String>> users = UserDAO.selectUsers();
//        MyAdapter adapter = new MyAdapter(users);
//        // findViewById maybe return null, so we need to check null before set the adapter for the listview to avod NullPointerException here
//        ((ListView) findViewById(R.id.list)).setAdapter(adapter);
//    }
//
//    // The Adapter class doesn't belong to the activity class, so it shouldn't be nested in the MainActivity class
//    public class MyAdapter extends ArrayAdapter<Pair<String, String>> {
//        // Cache data shouldn't save in the adapter object, because the cache data maybe very large,
//        // so it can make the app performance become bad. We should use LruCache to manage bitmap cache and
//        // should have the limit for memory cache.
//        // We should have a ImageCache go with bitmap worker to manage get image from file or cache task.
//        private Map<String, Bitmap> cache = new HashMap<>();
//
//        public MyAdapter(List<Pair<String, String>> list) {
//            super(MainActivity.this, R.layout.item, list);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // We should build a ViewHolder to hold the created itemView,
//            // because findViewById is a complex task it may cause the bad performance for the list view.
//            TextView tvName = convertView.findViewById(R.id.name);
//            ImageView ivProfileImg = convertView.findViewById(R.id.profileImg);
//
//            Pair<String, String> pair = getItem(position);
//            tvName.setText(pair.first);
//            ivProfileImg.setImageBitmap(getProfileImg(pair.second));
//            return convertView;
//        }
//
//        // Get Image task is a long running task, so we need to put it in background thread instead of main thread like this.
//        // We also need to have a bitmap worker to manage the get image task in background,
//        // instead of put getProfileImage inside adapter like this.
//        private Bitmap getProfileImg(String url) {
//            Bitmap bitmap = cache.get(url);
//            if (bitmap != null) return bitmap;
//
//            File file = ThumbDAO.downloadProfileImg(getContext(), url);
//            if (file == null) return null;
//
//            try {
//                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//    }
//
//}