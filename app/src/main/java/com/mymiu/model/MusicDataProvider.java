package com.mymiu.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.mymiu.utils.PinyinUtil;

import java.util.ArrayList;
import java.util.List;


public class MusicDataProvider {
    private Context context;
    private Cursor cursor;
    private List<MusicEntity> message;
    public MusicDataProvider(Context context){
        this.context=context;
    }
    public List<MusicEntity> getMusicData(){
        message=new ArrayList<>();
        ContentResolver contentResolver=context.getContentResolver();
        if(contentResolver!=null){
            cursor=contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        }
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    MusicEntity entity=new MusicEntity();
                    //歌曲名称
                    String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    //歌曲名转化为拼音
                    String pinyin= PinyinUtil.getPingYin(name);
                    entity.setPinYin(pinyin);
                    //获取首字母
                    String fPinyin=pinyin.substring(0, 1).toUpperCase();
                    //判断首字母是否是英文字母
                    if (fPinyin.matches("[A-Za-z]")) {
                        entity.setFirstPinYin(fPinyin);
                    } else {
                        entity.setFirstPinYin("#");
                    }
                    //专辑
                    String special = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    //歌手
                    String singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    //路径
                    String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    //时长
                    long time = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    //文件大小
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                    int id=cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    entity.setName(name);
                    entity.setSpecial(special);
                    entity.setSinger(singer);
                    entity.setSize(size);
                    entity.setTime(time);
                    entity.setUrl(url);
                    entity.setId(id);
                    message.add(entity);
                }while (cursor.moveToNext());
            }
        }
        return message;
    }
}
