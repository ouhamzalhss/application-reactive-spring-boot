// Randomly add a data point every 500ms
var courbes = [] ;
var chart = new SmoothieChart({interpolation:'linear'});
var index = -1;

var colors = [
    { strokeStyle: 'rgba(255, 0, 0, 1)', fillStyle: 'rgba(255, 0, 0, 0.2)', lineWidth: 4 },
    { strokeStyle: 'rgba(0, 255, 0, 1)', fillStyle: 'rgba(0, 255, 0, 0.2)', lineWidth: 4 },
    { strokeStyle: 'rgba(0, 0, 255, 1)', fillStyle: 'rgba(0, 0, 255, 0.2)', lineWidth: 4 },
    { strokeStyle: 'rgba(0, 255, 255, 1)', fillStyle: 'rgba(0, 255, 255, 0.2)', lineWidth: 4 }
];



function getColors(){
    index++;
    if(index>colors.length) index=0;
    return colors[index];
}

function onConnect(btn,id) {

    if(courbes[id]==null){
        courbes[id] = new TimeSeries();
        chart.addTimeSeries(courbes[id], getColors());
        chart.streamTo(document.getElementById("chart"), 500);

        var connection = new EventSource('/streamMatchesByTeam/'+id);
        connection.onmessage=function (response){
            var matche = JSON.parse(response.data);
            courbes[id].append(new Date().getTime(),matche.points);
        };
    }

    btn.style.background = '#FF0000';
    document.getElementById("photoDiv").style.display = "block";
    document.getElementById("image").src = "images/"+id+".jpg";


}