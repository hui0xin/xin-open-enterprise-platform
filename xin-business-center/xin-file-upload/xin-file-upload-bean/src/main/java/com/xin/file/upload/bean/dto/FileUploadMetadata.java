package com.xin.file.upload.bean.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class FileUploadMetadata {

    private Date lastModified;
    private Date rfc822Expires;
    private String expires;
    private long contentLength;
    private String contentType;
    private String contentMD5;
    private String contentEncoding;
    private String cacheControl;
    private String contentDisposition;
    private String etag;

    /**
     * 用户自定义的元数据，表示以x-xxx-meta-为前缀的请求头。
     */
    private final Map<String, String> userMetadata = new HashMap<>();

    /**
     * <p>
     * 获取用户自定义的元数据。
     * </p>
     * <p>
     * OSS内部保存用户自定义的元数据时，会以x-oss-meta-为请求头的前缀。
     * 但用户通过该接口处理用户自定义元数据里，不需要加上前缀“x-oss-meta-”。
     * 同时，元数据字典的键名是不区分大小写的，并且在从服务器端返回时会全部以小写形式返回，
     * 即使在设置时给定了大写字母。比如键名为：MyUserMeta，通过getObjectMetadata接口
     * 返回时键名会变为：myusermeta。
     * </p>
     *
     * @return 用户自定义的元数据。
     */
    public Map<String, String> getUserMetadata() {
        return this.userMetadata;
    }

    /**
     * 设置用户自定义的元数据，表示以x-oss-meta-为前缀的请求头。
     *
     * @param userMetadata 用户自定义的元数据。
     */
    public void setUserMetadata(final Map<String, String> userMetadata) {
        this.userMetadata.clear();
        if (userMetadata != null && !userMetadata.isEmpty()) {
            this.userMetadata.putAll(userMetadata);
        }
    }

    /**
     * 添加一个用户自定义的元数据。
     *
     * @param key   请求头的Key。
     *              这个Key不需要包含OSS要求的前缀，即不需要加入“x-oss-meta-”。
     * @param value 请求头的Value。
     */
    public void addUserMetadata(final String key, final String value) {
        this.userMetadata.put(key, value);
    }

    /**
     * 获取Last-Modified请求头的值，表示Object最后一次修改的时间。
     *
     * @return Object最后一次修改的时间。
     */
    public Date getLastModified() {
        return this.lastModified;
    }

    /**
     * 设置Last-Modified请求头的值，表示Object最后一次修改的时间（内部使用）。
     *
     * @param lastModified Object最后一次修改的时间。
     */
    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * 获取Expires响应头，返回其Rfc822日期表示形式。
     * 如果Object没有定义过期时间，则返回null。
     *
     * @return Expires响应头的Rfc822日期表示形式。
     */
    public Date getRfc822Expires() {
        return this.rfc822Expires;
    }

    /**
     * 设置Expires请求头。
     *
     * @param rfc822Expires 过期时间。
     */
    public void setRfc822Expires(final Date rfc822Expires) {
        this.rfc822Expires = rfc822Expires;
    }

    /**
     * 获取原始的Expires响应头，不对其进行日期格式解析，返回其字符串表示形式。
     * 如果Object没有定义过期时间，则返回null。
     *
     * @return 原始的Expires响应头。
     */
    public String getExpires() {
        return this.expires;
    }

    /**
     * 设置原始的Expires响应头，不对其进行日期格式解析，返回其字符串表示形式。
     * 如果Object没有定义过期时间，则返回null。
     */
    public void setExpires(final String expires) {
        this.expires = expires;
    }

    /**
     * 获取Content-Length请求头，表示Object内容的大小。
     *
     * @return Object内容的大小。
     */
    public long getContentLength() {
        return this.contentLength;
    }

    /**
     * 设置Content-Length请求头，表示Object内容的大小。
     * 当上传Object到OSS时，请总是指定正确的content length。
     *
     * @param contentLength Object内容的大小。
     */
    public void setContentLength(final long contentLength) {
        this.contentLength = contentLength;
    }

    /**
     * 获取Content-Type请求头，表示Object内容的类型，为标准的MIME类型。
     *
     * @return Object内容的类型，为标准的MIME类型。
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * 获取Content-Type请求头，表示Object内容的类型。
     *
     * @param contentType Object内容的类型。
     */
    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    /**
     * 获取content-MD5请求头，表示Object内容的类型。
     *
     * @return 获取content-MD5 Object内容的类型。
     */
    public String getContentMD5() {
        return this.contentMD5;
    }

    /**
     * 设置contentMD5请求头，表示Object内容的类型。
     *
     * @param contentMD5 content-MD5 Object内容的类型。
     */
    public void setContentMD5(final String contentMD5) {
        this.contentMD5 = contentMD5;
    }

    /**
     * 获取Content-Encoding请求头，表示Object内容的编码方式。
     *
     * @return Object内容的编码方式。
     */
    public String getContentEncoding() {
        return this.contentEncoding;
    }

    /**
     * 设置Content-Encoding请求头，表示Object内容的编码方式。
     *
     * @param encoding 表示Object内容的编码方式。
     */
    public void setContentEncoding(final String encoding) {
        this.contentEncoding = encoding;
    }

    /**
     * 获取Cache-Control请求头，表示用户指定的HTTP请求/回复链的缓存行为。
     *
     * @return Cache-Control请求头。
     */
    public String getCacheControl() {
        return this.cacheControl;
    }

    /**
     * 设置Cache-Control请求头，表示用户指定的HTTP请求/回复链的缓存行为。
     *
     * @param cacheControl Cache-Control请求头。
     */
    public void setCacheControl(final String cacheControl) {
        this.cacheControl = cacheControl;
    }

    /**
     * 获取Content-Disposition请求头，表示MIME用户代理如何显示附加的文件。
     *
     * @return Content-Disposition请求头
     */
    public String getContentDisposition() {
        return this.contentDisposition;
    }

    /**
     * 设置Content-Disposition请求头，表示MIME用户代理如何显示附加的文件。
     *
     * @param disposition Content-Disposition请求头
     */
    public void setContentDisposition(final String disposition) {
        this.contentDisposition = disposition;
    }

    /**
     * 获取一个值表示与Object相关的hex编码的128位MD5摘要。
     *
     * @return 与Object相关的hex编码的128位MD5摘要。
     */
    public String getETag() {
        return this.etag;
    }

    /**
     * 设置一个值表示与Object相关的hex编码的128位MD5摘要。
     *
     * @param eTag hex编码的128位MD5摘要。
     */
    public void setETag(final String eTag) {
        this.etag = eTag;
    }

}
