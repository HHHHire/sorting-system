//Chart

var ctx = document.getElementById('statisticsChart').getContext('2d');
var ctx2 = document.getElementById('statisticsChart2').getContext('2d');

var statisticsChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ["Mon", "Tues", "Wed", "Thur", "Fri", "Sat", "Sun"],
        datasets: [{
            label: "小明",
            borderColor: '#1acc7e',
            pointBackgroundColor: 'rgba(26, 204, 126, 0.2)',
            pointRadius: 0,
            backgroundColor: 'rgba(26, 204, 126, 0.1)',
            legendColor: '#1acc7e',
            fill: true,
            borderWidth: 3,
            data: [154, 184, 175, 203, 210, 231, 240]
        }, {
            label: "小强",
            borderColor: '#f55480',
            pointBackgroundColor: 'rgba(245, 84, 128, 0.2)',
            pointRadius: 0,
            backgroundColor: 'rgba(245, 84, 128, 0.1)',
            legendColor: '#f55480',
            fill: true,
            borderWidth: 3,
            data: [256, 230, 245, 287, 240, 250, 230]
        }, {
            label: "小张",
            borderColor: '#5e95fc',
            pointBackgroundColor: 'rgba(94, 149, 252, 0.2)',
            pointRadius: 0,
            backgroundColor: 'rgba(94, 149, 252, 0.1)',
            legendColor: '#5e95fc',
            fill: true,
            borderWidth: 3,
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
                    fontColor: "rgba(255,255,255,0.5)",
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
                    fontColor: "rgba(255,255,255,0.5)",
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
            borderColor: '#42e58e',
            pointBackgroundColor: 'rgba(66, 229, 142, 1)',
            pointRadius: 0,
            backgroundColor: 'rgba(66, 229, 142, 0.1)',
            legendColor: '#42e58e',
            fill: true,
            borderWidth: 3,
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
                    fontColor: "rgba(255, 255, 255 ,0.5)",
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
                    fontColor: "rgba(255, 255, 255 ,0.5)",
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