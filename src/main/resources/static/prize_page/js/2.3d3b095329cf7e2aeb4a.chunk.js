(window.webpackJsonp=window.webpackJsonp||[]).push([[2],{"2K1W":function(t,e,n){"use strict";var a=n("cDf5"),r=n.n(a),i=n("lwsE"),u=n.n(i),c=n("W8MJ"),s=n.n(c),o=new(function(){function t(){u()(this,t),this.eventList={}}return s()(t,[{key:"on",value:function(t,e,n){this.eventList[t]||(this.eventList[t]=[]);var a={};n?(a[n]=e,e=a):e.name&&(a[e.name]=e,e=a),this.eventList[t].push(e)}},{key:"remove",value:function(t,e){var n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:function(){};this.eventList[t]&&(this.eventList[t]=this.eventList[t].filter(function(t){return"object"===r()(t)?Object.keys(t)[0]!==(e||n.name):t}))}},{key:"removeAll",value:function(t){delete this.eventList[t]}},{key:"clear",value:function(){var t=this;Object.keys(this.eventList).map(function(e){return delete t.eventList[e]})}},{key:"emit",value:function(t,e){var n=this;this.eventList[t].map(function(t){"object"===r()(t)?Object.values(t).map(function(t){return t.call(n,e)}):t.call(n,e)})}}]),t}());o.init=function(){this.data={acname:"",status:"",typelist:[],untypelist:[]}},o.init();e.a=o},Nlzp:function(t,e,n){"use strict";n.d(e,"g",function(){return O}),n.d(e,"f",function(){return S}),n.d(e,"e",function(){return P}),n.d(e,"h",function(){return A}),n.d(e,"a",function(){return T}),n.d(e,"d",function(){return j}),n.d(e,"c",function(){return D}),n.d(e,"b",function(){return J}),n.d(e,"j",function(){return q}),n.d(e,"k",function(){return M}),n.d(e,"i",function(){return B});var a,r,i,u,c,s=n("RIqP"),o=n.n(s),p=n("o0o1"),d=n.n(p),l=n("MVZn"),m=n.n(l),f=n("yXPU"),h=n.n(f),v=n("J4zp"),k=n.n(v),y=n("vDqi"),g=n.n(y),z=n("fZJM"),w=n.n(z),L=n("2K1W"),b=n("xXzL"),_=function(t){var e=new FormData;return Object.entries(t).map(function(t){var n=k()(t,2),a=n[0],r=n[1];return e.append(a,r)}),e},x=function(t){for(var e,n,a=t.length;a;)n=Math.floor(Math.random()*a--),e=t[a],t[a]=t[n],t[n]=e;return t},O=function(t){var e=t.username,n=t.password,a=w.a.sha256().update("redrock"+n).digest("hex");return g()({method:"POST",url:"/prize/login",data:_({username:e,password:a})})},S=function(){return g()({method:"POST",url:"/prize/loginOut",data:_({token:b.a.getLocal("token")})})},P=(a=h()(d.a.mark(function t(){var e,n,a,r,i,u,c,s,o,p,l;return d.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return e=b.a.getLocal("token"),n=_({page:0,pagesize:5,token:e}),t.next=4,g.a.post("/prize/showActivity",n);case 4:return a=t.sent,r=a.data.data.filter(function(t){return 2==t.status}),t.next=8,Promise.all(r.map(function(){var t=h()(d.a.mark(function t(n){var a,r;return d.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a=n.actid,t.next=3,g.a.post("/prize/showTemp",_({actid:a,token:e}));case 3:return r=t.sent,t.abrupt("return",m()({},r.data.data,{actid:a}));case 5:case"end":return t.stop()}},t)}));return function(e){return t.apply(this,arguments)}}()));case 8:i=t.sent,u=i.map(function(t){return{actid:t.actid,acname:t.activity,status:2,typelist:t.typeA.map(function(t){return{mark:t.mark,prize_name:t.reward,prize_date:t.prizeDate,push_message:t.sendmsg,student_list:t.reqStudents,type:"\u6307\u5b9a\u7c7b\u578b"}}),untypelist:t.typeB.map(function(t){return{mark:t.mark,prize_name:t.reward,type:"\u975e\u6307\u5b9a\u7c7b\u578b"}})}}),c=a.data.data.filter(function(t){return 1===t.status}),s=c.map(function(t){return{acname:t.actname,qrlist:t.urls.map(function(t){return{url:t.url,prize:t.reward}})}}),o=u.concat(c.map(function(t){return{actid:t.actid,acname:t.actname,status:t.status}})),p=a.data.data.filter(function(t){return 3===t.status}),l=p.map(function(t){return{actid:t.actid,acname:t.actname}}),b.a.setLocal("dataList",o),b.a.setLocal("qrcodeList",s),b.a.setLocal("historyList",l),L.a.emit("renderInitialdataList",o);case 19:case"end":return t.stop()}},t)})),function(){return a.apply(this,arguments)}),A=function(t){var e=t.typelist.map(function(t){return{mark:t.mark,reward:t.prize_name,prizeDate:t.prize_date,sendmsg:t.push_message,reqStudents:t.student_list}}),n=t.untypelist.map(function(t){return{mark:t.mark,reward:t.prize_name}});t=JSON.stringify({activity:t.acname,acturl:"",token:b.a.getLocal("token"),typeA:e,typeB:n}),g()({method:"POST",url:"/prize/tempAct",headers:{"Content-Type":"application/json"},data:t})},T=(r=h()(d.a.mark(function t(e){var n,a,r,i,u,c,s,o;return d.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return n=g.a.create({headers:{"Content-Type":"application/json"}}),a=e.typelist.map(function(t){return{mark:t.mark,reward:t.prize_name,prizeDate:t.prize_date,sendmsg:t.push_message,reqStudents:t.student_list}}),r=e.untypelist.map(function(t){return{mark:t.mark,reward:t.prize_name}}),e=JSON.stringify({activity:e.acname,token:b.a.getLocal("token"),typeA:a,typeB:r}),t.next=6,n.post("/prize/specifiedAct",e);case 6:return i=t.sent,u=Object.entries(i.data.aactID).map(function(t){var n=k()(t,2),a=n[0],r=n[1];return{url:"http://zblade.top/prize/wx_operate/redirect_A/".concat(i.data.actid,"/").concat(r),prize:a,acname:e.acname,type:"\u6307\u5b9a\u7c7b\u578b"}}).concat(Object.entries(i.data.bactID).map(function(t){var n=k()(t,2),a=n[0],r=n[1];return{url:"http://zblade.top/prize/wx_operate/redirect_B/".concat(i.data.actid,"/").concat(r),prize:a,acname:e.acname,type:"\u975e\u6307\u5b9a\u7c7b\u578b"}})),c=u.map(function(t){return{url:t.url,reward:t.prize}}),s=JSON.stringify({token:b.a.getLocal("token"),actid:i.data.actid,acturls:c}),t.next=12,n.post("/prize/addActUrl",s);case 12:return i.data.msg&&(o=i.data.msg.map(function(t){return{stuname:t.stuname,college:t.college,stuid:t.stuid,telephone:t.telephone}}),b.a.setLocal("failMsg",o)),t.abrupt("return",u);case 14:case"end":return t.stop()}},t)})),function(t){return r.apply(this,arguments)}),j=function(t){g()({method:"POST",url:"/prize/EndActivity",data:_({actid:t,token:b.a.getLocal("token")})})},D=function(t){g()({method:"POST",url:"/prize/deleteTemp",data:_({actid:t,token:b.a.getLocal("token")})})},J=function(t){g()({method:"POST",url:"/prize/deleteActivity",data:_({actid:t,token:b.a.getLocal("token")})})},q=(i=h()(d.a.mark(function t(e,n){var a,r,i,u=arguments;return d.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a=u.length>2&&void 0!==u[2]?u[2]:10,t.next=3,g()({method:"POST",url:"/prize/showPrizerA",data:_({actid:e,page:n,pagesize:a,token:b.a.getLocal("token")})});case 3:return r=t.sent,i=r.data.data.map(function(t,e){return{"\u5e8f\u53f7":e+1,"\u9886\u5956\u7c7b\u578b":"\u6307\u5b9a\u7c7b\u578b","\u59d3\u540d":t.stuname,"\u5b66\u9662":t.college,"\u5b66\u53f7":t.stuid,"\u7535\u8bdd":t.telephone,"\u5956\u54c1\u540d\u79f0":t.reward,"\u63a8\u9001\u60c5\u51b5":t.push_status?"\u63a8\u9001\u5931\u8d25":"\u63a8\u9001\u6210\u529f","\u9886\u5956\u60c5\u51b5":t.status?"\u5df2\u9886\u53d6":"\u672a\u9886\u53d6"}}),t.abrupt("return",{items:i,total:r.data.total});case 6:case"end":return t.stop()}},t)})),function(t,e){return i.apply(this,arguments)}),M=(u=h()(d.a.mark(function t(e,n){var a,r,i,u=arguments;return d.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a=u.length>2&&void 0!==u[2]?u[2]:10,t.next=3,g()({method:"POST",url:"/prize/showPrizerB",data:_({actid:e,page:n,pagesize:a,token:b.a.getLocal("token")})});case 3:return r=t.sent,i=r.data.data.map(function(t,e){return{"\u5e8f\u53f7":e+1,"\u9886\u5956\u7c7b\u578b":"\u975e\u6307\u5b9a\u7c7b\u578b","\u59d3\u540d":t.stuname,"\u5b66\u9662":t.college,"\u5b66\u53f7":t.stuid,"\u7535\u8bdd":t.telephone,"\u5956\u54c1\u540d\u79f0":t.reward,"\u63a8\u9001\u60c5\u51b5":t.push_status?"\u63a8\u9001\u6210\u529f":"\u63a8\u9001\u5931\u8d25","\u9886\u5956\u60c5\u51b5":t.status?"\u5df2\u9886\u53d6":"\u672a\u9886\u53d6"}}),t.abrupt("return",{items:i,total:r.data.total});case 6:case"end":return t.stop()}},t)})),function(t,e){return u.apply(this,arguments)}),B=(c=h()(d.a.mark(function t(e,n){var a,r,i,u,c,s,p,l;return d.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a=10,r=666666,t.next=4,q(e,0,r);case 4:return i=t.sent,t.next=7,M(e,0,r);case 7:if(u=t.sent,c=x([].concat(o()(i.items),o()(u.items))).map(function(t,e){return t["\u5e8f\u53f7"]=e+1,t}),!n){t.next=14;break}return s=Math.ceil(c.length/a),p=n*a,l=c.filter(function(t,e){return p>=c.length?e+1>p-a:e+1>=p-9&&e+1<=p}),t.abrupt("return",{total:s,items:l});case 14:return t.abrupt("return",{items:c});case 15:case"end":return t.stop()}},t)})),function(t,e){return c.apply(this,arguments)})}}]);