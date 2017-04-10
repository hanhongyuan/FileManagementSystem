# 分布式文件管理接口

## 目录
* [1.接口](#1)
  - [1.1.文件数据接口](#1.1)
  -	[1.1.1 文件上传接口](#1.1.1)
  -	[1.1.1.1.单个文件上传接口](#1.1.1.1)
  -	[1.1.1.2.批量文件上传接口](#1.1.1.2)
  - [1.1.2.文件下载接口](#1.1.2)
  -	[1.1.2.1单个文件下载接口](#1.1.2.1)
  -	[1.1.2.2批量文件下载接口](#1.1.2.2)
  - [1.1.3.文件拷贝接口](#1.1.3)
  -	[1.1.3.1单个文件拷贝接口](#1.1.3.1)
  -	[1.1.3.2.批量文件拷贝接口](#1.1.3.2)
  - [1.1.4.文件/目录移动](#1.1.4)
  - [1.1.4.1.单个文件/目录移动接口](#1.1.4.1)
  -	[1.1.4.2.批量文件/目录移动接口](#1.1.4.2)
  - [1.1.5.文件搜索接口](#1.1.5)
  - [1.1.6.文件删除接口](#1.1.6)
  -	[1.1.6.1单个文件删除接口](#1.1.6.1)
  -	[1.1.6.2批量文件删除接口](#1.1.6.2)
  -	[1.2 结构化数据接口](#1.2)
## <span id="1">1.接口</span>

---

#### <span id="1.1">1.1.文件数据接口</span>

>   文件的上传、下载、拷贝、删除、搜索、移动

##### 请求地址
>  	http://localhost:8080/file
##### 请求方式
*   POST
##### 请求参数

##### 返回参数			
			
			}
#####<span id="1.1.1.1">1.1.1 单个文件上传接口</span>#####
>	该接口用于处理单个文件的上传

###### 请求地址
>	http://localhost:8080/file/upload/
###### 请求方式
###### 请求参数

| 参数名称       | 类型          | 是否必须 | 说明                          |
| :-------------:| :-----------: | :------: |:----------------------------- |
|method			 |String	     |Y	        |固定值upload					|
|user			 |String		 |Y			|用户名							|
|path			 |String		 |N 		|文件上传的路径					|
|file			 |byte[]		 |Y			|上传文件的内容(post表单提交,enctype属性使用multipart/form-data编码格式)	|
	注：参数method、user、path通过query_string提交，文件只能由表单提交,
    示例：
		带query_string的请求路径: http://localhost:8080/upload/file?method=upload&user=12345&path=/file/movie/redis.avi
		表单：
	 <form action="http://localhost:8080/file/upload" method="post"
          enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="hidden" name="mod" value="user"/>
    <input type="hidden" name="act" value="upload"/>
    <button type="submit">提交</button>
	</form>
			
######	返回参数
| 参数名称       | 类型         | UrlEncode |  说明                          |
| :-------------:| :-----------:|: -----:   | :----------------------------- |
|path			 |String		| Y         | 文件的绝对路径				 |
|size			 |String		| N			| 文件字节大小					 |
|MD5			 |String		| N         | 返回文件的数据md5签名	         |
|ctime           |String        | N         | 创建时间						 |
|code			 |String        | N			| 状态码成功200					 |
	示例：
			{
				"path":"/file/movie/The twlight.avi",
				"size":"1024b",
				"MD5":"550e8400e29b41d4a716446655440000",
				"ctime":"yyyy-MM-dd HH:mm:ss"
				"code":"200"
			
			}

#####<span id="1.1.1.1">1.1.1 批量文件上传接口</span>#####
>	该接口用于处理批量文件的上传

###### 请求地址
>	http://localhost:8080/file/batchupload
###### 请求方式
 POST
 Content-Type: multipart/form-data
###### 请求参数

| 参数名称       | 类型          | 是否必须 | 说明                          |
| :-------------:| :-----------: | :------: |:----------------------------- |
|method			 |String	     |Y	        |固定值batchupload				|
|user			 |String		 |Y			|用户名							|
|path			 |String		 |N 		|文件上传的路径					|
|file			 |byte[]		 |Y			|上传文件的内容(post表单提交,enctype属性使用multipart/form-data编码格式)	|
	注：参数method、user、path通过query_string提交，文件只能由表单提交,
    示例：
		带query_string的请求路径: http://localhost:8080/upload/file?method=upload&user=12345&path=/file/movie
		表单：
	 <form action="http://localhost:8080/upload/file" method="post"
          enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="hidden" name="mod" value="user"/>
    <input type="hidden" name="act" value="upload"/>
    <button type="submit">提交</button>
	</form>
			
######	返回参数
| 参数名称       | 类型         | UrlEncode |  说明                          |
| :-------------:| :-----------:|: -----:   | :----------------------------- |
|path			 |String		| Y         | 文件的绝对路径				 |
|size			 |String		| N			| 文件字节大小					 |
|MD5			 |String		| N         | 返回文件的MD5签名	         	 |
|ctime           |String        | N         | 创建时间						 |
|code			 |String        | N			| 状态码成功200
	示例：
		[
        {"filename:":"file1","path":"xxx","size":"xxx","ctime":"xxx","code":'200"},
        {"filename:":"file1","path":"xxx","size":"xxx","ctime":"xxx","code":'200"}
     
		]