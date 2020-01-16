//Chart

var ctx = document.getElementById('statisticsChart').getContext('2d');
var ctx2 = document.getElementById('statisticsChart2').getContext('2d');

var statisticsChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ["Mon", "Tues", "Wed", "Thur", "Fri", "Sat", "Sun"],
        datasets: [{
            label: "小明",
            borderColor: '#f3545d',
            pointBackgroundColor: 'rgba(243, 84, 93, 0.2)',
            pointRadius: 0,
            backgroundColor: 'rgba(243, 84, 93, 0.1)',
            legendColor: '#f3545d',
            fill: true,
            borderWidth: 2,
            data: [154, 184, 175, 203, 210, 231, 240]
        }, {
            label: "小强",
            borderColor: '#fdaf4b',
            pointBackgroundColor: 'rgba(253, 175, 75, 0.2)',
            pointRadius: 0,
            backgroundColor: 'rgba(253, 175, 75, 0.1)',
            legendColor: '#fdaf4b',
            fill: true,
            borderWidth: 2,
            data: [256, 230, 245, 287, 240, 250, 230]
        }, {
            label: "小张",
            borderColor: '#177dff',
            pointBackgroundColor: 'rgba(23, 125, 255, 0.2)',
            pointRadius: 0,
            backgroundColor: 'rgba(23, 125, 255, 0.1)',
            legendColor: '#177dff',
            fill: true,
            borderWidth: 2,
            data: [542, 480, 430, 550, 530, 453, 380]
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            display: false
        },
        tooltips: {
            bodySpacing: 4,
            mode: "nearest",
            intersect: 0,
            position: "nearest",
            xPadding: 10,
            yPadding: 10,
            caretPadding: 10
        },
        layout: {
            padding: {left: 15, right: 15, top: 15, bottom: 15}
        },
        scales: {
            yAxes: [{
                ticks: {
                    fontColor: "rgba(0,0,0,0.5)",
                    fontStyle: "500",
                    beginAtZero: false,
                    maxTicksLimit: 5,
                    padding: 20
                },
                gridLines: {
                    drawTicks: false,
                    display: false
                }
            }],
            xAxes: [{
                gridLines: {
                    zeroLineColor: "transparent"
                },
                ticks: {
                    padding: 20,
                    fontColor: "rgba(0,0,0,0.5)",
                    fontStyle: "500"
                }
            }]
        },
        legendCallback: function (chart) {
            var text = [];
            text.push('<ul class="' + chart.id + '-legend html-legend">');
            for (var i = 0; i < chart.data.datasets.length; i++) {
                text.push('<li><span style="background-color:' + chart.data.datasets[i].legendColor + '"></span>');
                if (chart.data.datasets[i].label) {
                    text.push(chart.data.datasets[i].label);
                }
                text.push('</li>');
            }
            text.push('</ul>');
            return text.join('');
        }
    }
});

var myLegendContainer = document.getElementById("myChartLegend");

// generate HTML legend
myLegendContainer.innerHTML = statisticsChart.generateLegend();

// bind onClick event to all LI-tags of the legend
var legendItems = myLegendContainer.getElementsByTagName('li');
for (var i = 0; i < legendItems.length; i += 1) {
    legendItems[i].addEventListener("click", legendClickCallback, false);
}

/**
 * 生成年图表
 */
new Chart(ctx2, {
    type: 'line',
    data: {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
            label: "当月数量",
            borderColor: '#177dff',
            pointBackgroundColor: 'rgba(23, 125, 255, 1)',
            pointRadius: 0,
            backgroundColor: 'rgba(23, 125, 255, 0.1)',
            legendColor: '#177dff',
            fill: true,
            borderWidth: 2,
            data: [542, 480, 430, 550, 530, 453, 380, 434, 568, 610, 700, 900]
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            display: false
        },
        tooltips: {
            bodySpacing: 4,
            mode: "nearest",
            intersect: 0,
            position: "nearest",
            xPadding: 10,
            yPadding: 10,
            caretPadding: 10
        },
        layout: {
            padding: {left: 15, right: 15, top: 15, bottom: 15}
        },
        scales: {
            yAxes: [{
                ticks: {
                    fontColor: "rgba(0,0,0,0.5)",
                    fontStyle: "500",
                    beginAtZero: false,
                    maxTicksLimit: 5,
                    padding: 20
                },
                gridLines: {
                    drawTicks: false,
                    display: false
                }
            }],
            xAxes: [{
                gridLines: {
                    zeroLineColor: "transparent"
                },
                ticks: {
                    padding: 20,
                    fontColor: "rgba(0,0,0,0.5)",
                    fontStyle: "500"
                }
            }]
        },
        legendCallback: function (chart) {
            var text = [];
            text.push('<ul class="' + chart.id + '-legend html-legend">');
            for (var i = 0; i < chart.data.datasets.length; i++) {
                text.push('<li><span style="background-color:' + chart.data.datasets[i].legendColor + '"></span>');
                if (chart.data.datasets[i].label) {
                    text.push(chart.data.datasets[i].label);
                }
                text.push('</li>');
            }
            text.push('</ul>');
            return text.join('');
        }
    }
});

$(".disk-box").click(function () {
    let gaugeData = $(this).find("div").find("i").attr("gauge-data");
    Notiflix.Report.Success(
        '当前类别',
        gaugeData,
        '确定'
    );
});