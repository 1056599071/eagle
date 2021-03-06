package eagle.jfaster.org.util;
import eagle.jfaster.org.rpc.support.EagleResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static eagle.jfaster.org.constant.EagleConstants.*;
/**
 * 对请求和回复体进行预处理
 *
 * Created by fangyanpeng1 on 2017/7/27.
 */
public class RequestUtil {

    public static boolean isRequest(short magicCode){
        return (magicCode & EAGLE_TYPE_REQ) == EAGLE_TYPE_REQ;
    }

    public static  boolean isRequestWithParameter(short magicCode){
        return (magicCode & EAGLE_REQ_PARAMETER) == EAGLE_REQ_PARAMETER;
    }

    public static boolean isCompress(short magicCode){
        return (magicCode & EAGLE_COMPRESS_TYPE) == EAGLE_COMPRESS_TYPE;
    }

    public static boolean  isNotIllegal(short magicCode){
        return (magicCode & EAGLE_MAGIC_MASK) != EAGLE_MAGIC_CODE;
    }

    public static boolean  isNormalValue(short magicCode){
        return (magicCode & EAGLE_RESPONSE_TYPE) == EAGLE_RESPONSE_NORMAL;
    }

    public static boolean  isVoidValue(short magicCode){
        return (magicCode & EAGLE_RESPONSE_TYPE) == EAGLE_RESPONSE_VOID;
    }

    public static EagleResponse buildExceptionResponse(int opaque,Exception e){
        EagleResponse response = new EagleResponse();
        response.setOpaque(opaque);
        response.setException(e);
        return response;
    }


    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(out);
        gos.write(data);
        gos.finish();
        gos.flush();
        gos.close();
        byte[] ret = out.toByteArray();
        return ret;
    }

    public static byte[] unCompress(byte[] data,int len) throws IOException {
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(data));
        byte[] ret = new byte[len];
        gis.read(ret);
        gis.close();
        return ret;
    }


}
