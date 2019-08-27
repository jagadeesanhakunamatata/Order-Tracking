package com.inrange.trackapplication.module.home;

import android.location.Location;

import com.inrange.trackapplication.retrofit.BaseModel;


public class MainModelImpl extends BaseModel implements MainModel {

    private static final String TAG = MainModelImpl.class.getSimpleName();
    private MainModelListener mMainModelListener;
    private String mGeoLocation;

    private MainModelImpl(MainModelListener mainmodellistener) {
        mMainModelListener = mainmodellistener;

    }

    public static MainModel getInstance(MainModelListener mainmodellistener) {
        return new MainModelImpl(mainmodellistener);
    }

    @Override
    public void onSaveUpdatedLocation(Location location) {
        mGeoLocation = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
    }


    /*public void getImageGalleryFile() {
        List<FileDto> imageGalleryFileList = new ArrayList<>();

        final boolean[] isCompleted = new boolean[1];

        String folderArray[] = {};
        if (DataHandler.getInstance().getFolders() != null) {
            folderArray = DataHandler.getInstance().getFolders().split(",");
        }
//        if (isphotoGallery) {

        for (String folder : folderArray) {
            if (folder.equals("slbpvt")) {
                folder = folder.toUpperCase();
            } else {
                folder = folder.substring(0, 1).toUpperCase() + folder.substring(1);
            }
            Log.e(TAG, "getImageGalleryFile: " + folder);

            Cursor cursor = mModelListener.getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media.DATA + " like ? ", new String[]{"%" + folder + "%"}, MediaStore.Images.Media.DATE_MODIFIED + " ASC");

            String imageData;
            while (cursor.moveToNext()) {
                FileDto fileDto = new FileDto();
                imageData = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                fileDto.setFileName(imageData);
                imageData = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                fileDto.setImagePath(imageData);
                long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                fileDto.setModifiedDate(date);
                fileDto.setFileType(Constants.FileType.FILE_TYPE_IMAGE);

                File file = new File(fileDto.getImagePath());

                if (DataHandler.getInstance().getAutoUpload() && FileDao.getInstance().getPaginateList("SELECT * FROM " +
                        QueryManager.TableNames.FILES + " where fileName = ?", new String[]{fileDto.getFileName()}) == null && file.exists()) {
                    mAutoUploadList.add(fileDto);
                }

                FileDao.getInstance().insertSingleFileInfo(fileDto);
            }
            cursor.close();
        }
//        } else {

        for (String folder : folderArray) {

            if (folder.equals("slbpvt")) {
                folder = folder.toUpperCase();
            }

            Log.e(TAG, "getImageGalleryFile: " + folder);
//                folder = folder.substring(0, 1).toUpperCase() + folder.substring(1);

            Cursor cursor = mModelListener.getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Video.Media.DATA + " like ? ", new String[]{"%" + folder + "%"}, MediaStore.Video.Media.DATE_MODIFIED + " ASC");

            String imageData;
            while (cursor.moveToNext()) {
                FileDto fileDto = new FileDto();
                imageData = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                fileDto.setFileName(imageData);
                imageData = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                fileDto.setImagePath(imageData);
                long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));
                fileDto.setModifiedDate(date);
                fileDto.setFileType(Constants.FileType.FILE_TYPE_VIDEO);

                File file = new File(fileDto.getImagePath());

                if (DataHandler.getInstance().getAutoUpload() && FileDao.getInstance().getPaginateList("SELECT * FROM " +
                        QueryManager.TableNames.FILES + " where fileName = ?", new String[]{fileDto.getFileName()}) == null && file.exists()) {
                    mAutoUploadList.add(fileDto);
                }

                FileDao.getInstance().insertSingleFileInfo(fileDto);
            }
            cursor.close();
        }
    }*/


    interface MainModelListener {


    }

}
