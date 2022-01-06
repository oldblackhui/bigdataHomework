import requests
import pandas as pd

pd_data = pd.DataFrame()
for page in range(1, 12):
    print(page)
    url = 'https://opendata.sz.gov.cn/api/29200_00403621/1/service.xhtml?page=' + str(
        page) + '&rows=100&appKey=???????'
    strhtml = requests.get(url)
    strhtml.encoding = 'utf8'
    dic = strhtml.json()
    # print(dic)
    for i in range(0, len(dic['data'])):
        pd_temp = pd.DataFrame.from_dict(dic['data'][i], orient='index').T
        # print(pd_temp)
        pd_data = pd_data.append(pd_temp, ignore_index=True)
try:
    #print(pd_data)
    pd_data.to_csv('D:\Pythonproject\pythonProject\深圳车辆test.csv', mode='a', index=False, header=False)
except Exception as e:
    print(e)
