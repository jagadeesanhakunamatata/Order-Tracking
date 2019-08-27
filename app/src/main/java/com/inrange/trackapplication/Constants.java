package com.inrange.trackapplication;

/**
 * Created by hmspl on 3/7/17.
 */

public interface Constants {



    public static final String BASE_URL = "http://99.226.247.195:8080/api/v1/";
    interface AppKeys {
        String DESTINATION_FOLDER_NAME = "SLBPVT";
        String COMPRESSVIDEO_FOLDER_NAME = ".CompressedFile";
        String FILE_PROVIDER_AUTHORITY = "com.curveball.smartjo.lv.fileprovider";
        String LIVE_STREAMING_FOLDER_NAME = "Streaming"; // added by john for live streaming to store capture images


        public String IS_LOGGED_IN = "is_logged_in";
        public String USERNAME = "USERNAME";
        public String PWD = "PWD";

    }
    interface Status {
        String CANCELLED = "Cancelled";
        String COMPLETED = "Completed";
        String MOVETODELIVERY = "Move To Delivery";
    }
    interface Roles {
        String ROLE_CUSTOMER = "ROLE_CUSTOMER";
        String ROLE_ADMIN = "ROLE_ADMIN";
        String ROLE_COURIER = "ROLE_COURIER";
    }



    interface AppKey {
        String EXTERNAL_FOLDER_NAME = "oneway/drivers";
        String GOOGLE_API_KEY = "";
        String MODE = "ANDROID";
        String APP_TOKEN = "DAPP";
        String FILE_PROVIDER_AUTHORITY = "com.oneway.drivers.fileprovider";
        String LOG_FILE_NAME = "onewayLogs.txt";
        String WAITING_REQUEST = "waiting_request";
        String FORCE_REQUEST = "force_request";
        String FORCE_UPDATE = "FORCE_UPDATE";
        String SIGNOUT_EXPIRY = "signout_expiry";
        String FORCE_TRIP_STATUS = "force_trip_status";
        int OFF_DUTY = 0;
        int ON_DUTY = 1;

        int GO_HOME = 2;
        String SOCKET_CONNECTION_CHECK = "SOCKET_CONNECTION_CHECK";
        String WAKE_APP = "WAKE_APP";

        public String IS_LOGGED_IN = "is_logged_in";
        public String USERNAME = "USERNAME";
        public String PWD = "PWD";

    }

    interface SharedKeys {
        String IS_LOGIN_COMPLETED = "isLoginCompleted";
        String AUTO_UPLOAD = "auto_upload";
        String AUTO_DELETE_DAYS = "auto_delete_days";
        String IMAGE_QUALITY = "image_quality";
        String VIDEO_QUALITY = "video_quality";
        String DEFAULT_TAG = "default_tag";
        String EDITION = "edition";
        String CAMERA_SERIAL_NO = "camera_serial_no";
        String SESSION_INTERVAL = "session_interval";
        String WEB_LINK = "Web_link";
        String WEB_LINK_TEXT = "web_link_text";
        String TAGS = "tags";
        String USER_TAGS = "user_tags";
        String FOLDERS = "folders";
        String FC_HOST = "fc_host";
        String FC_PORT = "fc_port";
        String FC_USERNAME = "fc_user_name";
        String FC_PASSWORD = "fc_password";
        String FC_PATH = "fc_path";
        String FC_IMAGE_PATH = "fc_image_path";
        String FC_VIDEO_PATH = "fc_video_path";
        String USER_NAME = "user_name";
        String FOOT_NOTE = "foot_note";
        String INTERNAL_NOTE = "internal_note";
        String PHOTO_GRAPHER = "photo_grapher";
        String XML_DESK = "xml_desk";
        String DEFAULT_LOCATION = "default_location";
        String NEW_VERSION_URL = "newVersionURL";
        String UPDATABLE_VERSION = "updatableVersion";
        String DATA_UPDATE = "dataUpdate";
        String PHOTO = "photo";
        String VIDEO = "video";
        String REPORTER = "reporter";
        String PUBLICATION = "publication";
        String USER_PUBLICATION = "user_publication";
        String PRINTCENTER = "printcenter";
        String USER_PRINTCENTER = "user_printcenter";
        String PRINT_EDITION = "print_edition";
        String USER_EDITION = "user_edition";
        String PRINT_DESK = "print_desk";
        String DISABLE_PRINT_DESK = "disable_print_desk";
        String USER_DESK = "user_desk";
        String ELEMENTS_TEXT = "elements_text";
        String ELEMENTS_QUOTE = "elements_quote";
        String ELEMENTS_BOX = "elements_box";
        String FC_STO_PATH = "FC_sto_path";
        String IMAGE_SAVE_NAME = "image_save_name";
        String TEMP_IMAGE_PATH = "temp_image_path";
        String ARTICLE_ID = "article_id";
        String IS_UPLOAD_OPEN = "is_upload_open";
        String IMAGE_LOAD_SUCCESS = "image_load_success";
        String GENERAL_TAGS = "general_tags";

        String VIDEO_FOOT_NOTE = "video_foot_note";
        String VIDEO_INTERNAL_NOTE = "video_internal_note";

        //For Article added by John
        String ARTICLE_NAME = "article_name";
        String ARTICLE_HEADING = "article_heading";
        String ARTICLE_DECK = "article_deck";
        String ARTICLE_SECOND_HEADING = "article_second_heading";
        String ARTICLE_TEXT = "article_text";
        String ARTICLE_QUOTE = "article_quote";
        String ARTICLE_BOX = "article_box";

        //for login added by john
        String LOGIN_USER_NAME = "login_user_name";
        String LOGIN_USER_PASSWORD = "login_user_password";

        String FOCUS = "focus";
        String VIDEO_BITRATE = "video_bitrate";
        String AUDIO_BITRATE = "audio_bitrate";
        String AUDIO_SAMPLING = "audio_sampling";
        String VIDEO_SIZE = "video_size";
        String FPS = "fps";
        String TCI = "tci";
        String ORIENTATION = "orientation";
        String GOP = "gop";
        String AUDIO_CHANNEL = "audio_channel";
    }


    interface DatabaseKeys {
        String DATABASE_NAME = "smartjo.db";
        int DATABASE_VERSION = 2;
        String DATABASE_FILE = "smartjo.sql";
    }

    interface BundleKey {
        String ARTICLE_LIST = "articleList";
        String FILE_LIST = "file_list";
        String SELECTED_TAB = "selectedTab";
        String INDEX = "index";
        String USER_NAME = "userName";
        String PASSWORD = "password";
        String IMAGE_WIDTH = "imageWidth";
        String IMAGE_HEIGHT = "imageHeight";
        String IMAGES_NAME_ARRAY = "images_name_array";
        String INTERNAL_NOTE = "internal_note";
        String BREF_NOTE = "bref_note";
        String TAGS = "tags";
        String LOCATION = "location";
        String PHOTO = "photo";
        String VIDEO = "video";
        String FILEDTO = "filedto";
        String AUTO_STOP = "auto_stop";
        String DELETED_PHOTOS = "deletedPhotos";
        String IS_EDITED = "isEdited";
        String VIDEO_PATH = "videoPath";
        String IS_FROM_IMPORT = "isFromImport";
        String IS_FROM_GALLERY = "isFromGallery";
        String IS_FROM = "isFrom";
        String IS_TO_SHOW = "isToShow";
        String CAMERA_DETACHED = "cameraDetached";
        String IS_REUPLOAD = "reUpload";
        String FILE_DOWNLOAD_URL = "fileDownloadUrl";
        String FILE_DOWNLOAD_HOST = "fileDownloadHost";
        String FILE_URL = "fileUrl";
        String SELECTED_IMAGE = "selectedImages";
        String ARTICLE_ID = "articleID";
        String ARTICLE_SELECTION_LIST = "articleSelectionList";
        String POSITION = "position";
        String SELECTION_START = "selectionStart";
        String SELECTION_END = "selectionEnd";
        String SELECTION_TEXT = "selectionText";
        String INITIALIZE_DATABASE = "initializeDataBase";
        String IS_FROM_UPLOAD_CONFIRM = "isFromUploadConfirm";
        String UPLOAD_COMPLETE = "upload_complete";
        String ARTICLE_DTO = "article_dto";
        String IS_NEED_TO_TRIM = "is_need_to_trim";
        String ASSIGNMENTS = "assignments";
        String ASSIGNMENT = "assignment";
        String TYPEOFLIST = "typeofist";
        String ORDER = "ORDER";
    }

    interface CommonKeys {
        String TAGS_SEPERATOR = ",";
        String SERVICE_ACTION = "com.curveball.smartjo.FileUploadingService";
        String QUEUE_ACTION = "com.curveball.smartjo.queueactivity";
        String REPORT_UPLOAD_ACTION = "com.curveball.smartjo.reportuploadfragment";
        String STOP_ALL_ACTION = "stop_all_action";
        String PAUSE_ACTION = "pause_action";
        String CANCEL_ACTION = "cancel_action";
        String PLAY_ACTION = "play_action";
        String COMPLETED_ACTION = "completed_action";
        int UPLOAD_BAUDRATE = 10000;
        String LOCAL_FC_PASSWORD = "123456";
        String LOCAL_FC_USERNAME = "user";
        String LOCAL_FC_SERVER_IP = "192.168.2.35";
        int LOCAL_FC_SERVER_PORT = 21;
        int PERCENTAGE = 100;
        int UPLOAD_NOTIFI_ID = 12345;
        long RETRY_TIMING = 120000;
        String NOTIFICATION_ACTION = "notification_action";
        String HOME_START = "onHomeStart";
        String RE_UPLOAD = "reUpload";
        String ACCEPT_ALL_CAMERA = "All";
        String VIDEO_QUALITY_480 = "480";
        String VIDEO_QUALITY_360 = "360";
        String VIDEO_QUALITY_240 = "240";
        String VIDEO_QUALITY_720 = "720";
        String ORGINAL = "100";
        String MP4 = "MP4";
        String MP3 = "MP3";
        int DOWNLOAD_NOTI_ID = 123;
        int DOWNLOAD_COMPLETED_NOTI_ID = 198;
        String UPLOAD_ACTION = "com.curveball.smartjo.queueactivity";
    }

    interface UploadUtils {

        String LOG_TAG = "BackgroundThread";
        int MESSAGE_ID = 1;
        int COMPLETED_MESSAGE_ID = 2;
        String MESSAGE_BODY = "MESSAGE_BODY";
        String EMPTY_MESSAGE = "<EMPTY_MESSAGE>";
        int REMAINING_MESSAGE_ID = 3;
        int FAILED_MESSAGE_ID = 4;
        int UPLOAD_STATS_MSG_ID = 5;
        int UPLOAD_COMPLETED_MSG_ID = 6;
        int UPLOAD_STOPPED_MSG_ID = 7;
        int COMPRESSING_MSG_ID = 8;
        int XML_UPLOAD_COMPLETED_MSG_ID = 9;
    }

    interface ErrorCode {
        //custom error codes for internal data handling
        int ERROR = 1443;
        int DATA_NOT_FOUND = 1444;
        int JSON_PARSING = 1445;
        int SERVICE_UNAVAILABLE = 1447;
        int SERVER_CONNECTION_FAILED = 1448;
        int TIME_OUT = 1449;

        //defined error codes from api
        int SESSION_EXPIRED = 401;
    }

    interface SuccessCode {
        int RESULT_OK = 200;
    }

    interface AlertMessage {
        //common messages for api failure
        String NO_INTERNET_CONNECTION = "Please check your internet connection and try again.";
        String DATA_NOT_FOUND = "No data found. Contact our support center.";
        String JSON_PARSING = "Unable to send data to our server. Contact our support center.";
        String TIME_OUT = "Unable to connect to our server. Make sure you are having a stable internet connection.";
        String SERVER_CONNECTION_FAILED = "Unable to connect to our server. Make sure you are having a stable internet connection.";
        String UNABLE_TO_CONNECT_SERVER = "Unable to connect to our server. Please try again later.";
        String LOG_OUT_TITLE = "Log Out?";
        String LOG_OUT_CONTENT = "Do you want to Continue?";
        String LOG_IN_EXPIRED_TITLE = "Login Expired!";
        String LOG_IN_EXPIRED_CONTENT = "Oops! User logged in other device.";
        String YES = "YES";
        String CANCEL = "CANCEL";
        String UPLOAD_FAILED = "Upload Failed";
    }

    interface uploadStatus {
        String NEW_IMAGE = "newImage";
        String Completed = "Completed";
        String IN_PROGRESS = "InProgress";
        String Failed = "Failed";
    }

    interface DateFormat {
        String TIME_ZONE_GMT = "GMT";
        String DHINAMALAR_DATE_FORMAT = "MMMM dd, yyyy";
        String SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";
        String ASSIGNMENT_DATETIME_FORMAT = "hh:mm a | dd MMM yy";
        String ASSIGNMENT_DATE_FORMAT = "dd MMM yy";
        String ASSIGNMENT_CLOSED_DATE_FORMAT = "HH:MM a, dd MMMM yy";
    }

    interface EditedName {

        String IMAGE_EDIT_NAME = "_edit_";

    }

    interface FileType {

        String FILE_TYPE_VIDEO = "video";
        String FILE_TYPE_IMAGE = "image";
        String FILE_TYPE_AUDIO = "audio";
        String FILE_TYPE_BOTH = "both";
        String FILE_TYPE_SELECTED = "selected";

    }

    interface fileName {
        String DOT = "\\.";
        String FILE_NAME_DIVIDER = "_";
        String LIVE_STREAMING = "LIVE"; // added by john for live streaming to store image

    }
//
    interface MenuItems {
        int ORDERS = R.string.menu_orders;
        int ORDERS_MAP_VIEW = R.string.menu_map_orders;
        int SETTINGS = R.string.menu_settings;
        int ABOUT = R.string.menu_about;

        int[] allMenus = {ORDERS, ORDERS_MAP_VIEW, SETTINGS, ABOUT};
    }
}
