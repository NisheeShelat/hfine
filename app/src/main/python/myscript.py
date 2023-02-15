from os.path import dirname, join
import googletrans
from googletrans import Translator
import string
import re
import pandas as pd
import nltk
import numpy as np
import csv
# import required module
# wn = nltk.WordNetLemmatizer()
# query_lemmatize = [wn.lemmatize(word) for word in query_nostopword]

nltk.download('stopwords')
nltk.download('wordnet')
def sort_index(lst, rev=True):
    index = range(len(lst))
    s = sorted(index, reverse=rev, key=lambda i: lst[i])
    return s
def translate(inp):
    translator = Translator()
    result = translator.translate(inp,dest='en')
    query=result.text
    query=query.split("\t")
    query_nopunct="".join([char for char in query if char not in string.punctuation])
    query_tokens= re.split('\W',query_nopunct)
    stopword=nltk.corpus.stopwords.words('english')
    query_nostopword=[word for word in query_tokens if word not in stopword]
    
    #prediction
    data1=[]
    filename= join(dirname(__file__),"disease_severity2.csv")
    with open(filename,'r', encoding='utf-8', errors="ignore") as fin:
        data1=fin.read().lower()
    ds = pd.read_csv(filename)
    ds= ds.fillna("none")
    data = ds.iloc[:,1:]
    symptom=query_nostopword
    count=0
    count1=0
    severity_y=0
    list_of_symptom=[]
    for i, row in ds.iterrows():
        no_of_symptom=0
        severity_y=0
        for x in range(0,len(symptom)):
            count1=0
            for y in range(1,8):
                if row['Symptom_'+str(y)] != 'none':
                    #print(row['Symptom_'+str(y)])
                    count1 = count1 + 1
                if symptom[x] in row['Symptom_'+str(y)]:
                    #print(symptom[x])
                    #print("index",i)
                    count=count+1
                    severity_y=severity_y+y
                    
        #print("percentage",(severity_y/count1))
        #print("count1",count1)
        list_of_symptom.append(severity_y/count1)
    #print(list_of_symptom)
    calculation=list_of_symptom.copy()
    #print(calculation)
    index_sort_list=sort_index(calculation)[0:3]
    #print(index_sort_list)
    sum=0
    percentage=[]
    for i in index_sort_list:
        sum= sum + calculation[i]
    for i in index_sort_list:
        percentage.append((calculation[i]/sum)*100)
    #print(percentage)
    predicted_disease=[]
    for i in index_sort_list:
        predicted_disease.append(ds.iloc[i][0])
    #print(predicted_disease)


    str3= ' '.join([str(elem) for elem in calculation])
    str2=' '.join([str(elem) for elem in query_nostopword])
    str1=' '.join([str(elem) for elem in predicted_disease])

    
    return str1+str2+str3
    

