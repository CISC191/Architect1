window.stockChart = (function () {
    var ctx = document.getElementById('chart').getContext('2d');
    ctx.canvas.width = 1000;
    ctx.canvas.height = 250;

    // function lineData() { return barData.map(d => { return { x: d.x, y: d.c} }) };

    var chart = new Chart(ctx, {
        type: 'candlestick',
    });

    return chart;
})();

let previousResolution = null;

function getResolutionButton(resolution) {
    return document.getElementById(resolution + "-button");
}

function resetZoom() {
    window.stockChart.resetZoom();
}

async function changeResolution(resolution) {
    if (previousResolution === resolution) return;

    if (previousResolution !== null) {
        let previousResolutionButton = getResolutionButton(previousResolution)
        previousResolutionButton.classList.remove("bg-gray-400");
        previousResolutionButton.classList.add("hover:bg-gray-400");
        console.log(previousResolutionButton);
    }

    let resolutionButton = getResolutionButton(resolution);
    resolutionButton.classList.remove("hover:bg-gray-400");
    resolutionButton.classList.add("bg-gray-400");
    previousResolution = resolution;

    // if TICKER is null then return
    if (!TICKER) return;
    let candles = await (await fetch("/api/stocks/candles/" + TICKER + "?resolution=" + resolution)).json();
    let barData = [];
    for (candle of candles) {
        let [c, h, l, o, x, _] = candle;
        x = new Date(x * 1000);
        // offsite 1 day and 8 hours to match the requested date and time
        // -8 hours to match parcific time zone
        x.setHours(x.getHours() - 8 - 24);
        barData.push({ c, h, l, o, x: x.valueOf() });
    }

    var ctx = document.getElementById('chart').getContext('2d');
    ctx.canvas.width = 1000;
    ctx.canvas.height = 250;

    // function lineData() { return barData.map(d => { return { x: d.x, y: d.c} }) };

    window.stockChart.destroy();
    window.stockChart = new Chart(ctx, {
        type: 'candlestick',
        data: {
            datasets: [{
                label: TICKER + ' - stock candles',
                data: barData
            }]
        },
        options: {
            plugins: {
                zoom: {
                    pan: {
                        enabled: true,
                        mode: 'xy',
                        modifierKey: 'shift',
                    },
                    zoom: {
                        drag: {
                            enabled: true
                        },
                        mode: 'xy',
                        rangeMin: {
                            x: candles.at(0).x,
                            y: candles.at(0).l
                        },
                        rangeMax: {
                            x: candles.at(-1).x,
                            y:candles.at(-1).h
                        },

                        speed: 0.1,
                        threshold: 2,
                        sensitivity: 3,
                    }
                }
            }
        },

    })
}

changeResolution("day");