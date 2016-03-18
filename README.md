# wtshanxun
#豌豆荚地址：
http://www.wandoujia.com/apps/cn.mandroid.wtshanxun
#心跳接口：
## request
Url: http://sx.mandroid.cn/index.php/heart/getByRouter
Method: POST/GET
Query:
  user=[闪讯账号]
  ip=[IP地址]
如：http://sx.mandroid.cn/index.php/heart/getByRouter?user=123456@ABC.XY&ip=192.168.1.1
## response:
    {
    	"status": 200,
    	"data": {
    		"packData": "U04AYwOCQpY85mPaVnecguWKlDde4wIAB8CoAQEDAAwxLjIuMTguMjgUACNiNTcyYjI1NjEwODA4ZGE1N2QyY2Y0YWEyMGViZmE2ORIAB1br6zcBABAxMjM0NTZAQUJDLlhZ",  //base64encode
    		"sendIp": "115.239.134.167",
    		"sendPort": 8080
    	}
    }
