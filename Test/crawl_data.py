import requests
from bs4 import BeautifulSoup

payload = {
                                                                                'inUserName': 'Mangopiedia',
                                                                                'inUserPass': 'Voimamut2019!W09a'
}
with requests.Session() as s:
    p = s.post('https://vi.wikipedia.org/w/index.php?returnto=Trang+Ch%C3%ADnh&title=%C4%90%E1%BA%B7c_bi%E1%BB%87t:%C4%90%C4%83ng_nh%E1%BA%ADp&centralAuthAutologinTried=1&centralAuthError=Not+centrally+logged+in', data=payload)
    # print the HTML returned or something more intelligent to see if it's a successful login page.
    print(p.text)

    # An authorised request.
    response = s.get('A protected web page URL')
    # print(response.text)
# response = requests.get("https://qldt.ptit.edu.vn/#/tkb-tuan")

    print(response)
    print(response.text)
    print(response.content)

    soup = BeautifulSoup(response.content, "html.parser")
    print("##############################################")
    print(soup)