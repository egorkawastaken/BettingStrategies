package com.bettingstrategies.data.repository


import android.content.Context
import com.bettingstrategies.R
import com.bettingstrategies.data.db.BetDataBase
import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.data.repository.local.LocalDataSourceRepository
import com.bettingstrategies.domain.model.BettingStrategy
import com.bettingstrategies.utils.extensions.transformToModel


class LocalDataSourceImpl(val dataBase: BetDataBase, val context: Context) :
    LocalDataSourceRepository {

    override suspend fun getAllStrategies() = dataBase.getBetDao().getAllStrategies()


    override suspend fun getAllFavouriteStrategies(): List<BettingStrategyEntity> {
        val listOfEntities = dataBase.getBetDao().getAllFavouriteStrategies()
        return listOfEntities
    }

    override suspend fun getStrategyById(id: Int): BettingStrategy =
        dataBase.getBetDao().getStrategy(id = id).transformToModel()

    override suspend fun updateFavouriteStatus(strategyEntity: BettingStrategy) {
        val oldStrategy = dataBase.getBetDao().getStrategy(id = strategyEntity.id)

        oldStrategy.apply {
            val newStrategy = this.copy(isFavourite = this.isFavourite.not())
            dataBase.getBetDao().insertStrategy(newStrategy)

        }
    }

    override suspend fun insertStrategy() {
        var i = 0
        context.resources.getStringArray(R.array.strategies_names).forEach {
            dataBase.getBetDao().insertStrategy(
                BettingStrategyEntity(
                    title = it,
                    description = context.resources.getStringArray(R.array.strategies_description)[i],
                    urlToImage = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMSEhUTEhMWFhUXGR0YGBcYGB0fHxsYHRgaHxobGxsfHSggHyAlHRofITEhJSkrLi4vGCUzODMvNygtLisBCgoKDg0OGxAQGy0lICUtKy0vLS0vLS0tLS0tLS0tLS8tLS0tLTUtLS0tLS0tLS02LS0vLS0tLS0tLTAtLS0vLf/AABEIAJ0BQgMBIgACEQEDEQH/xAAcAAACAgMBAQAAAAAAAAAAAAAEBQMGAQIHAAj/xABFEAACAgAFAgQDBQUFBwIHAQABAgMRAAQSITEFQRMiUWEGMnEUI0KBkQdSYnKhFTNDsdEkU2OCksHw4fE0c4OTo7LiVP/EABoBAAMBAQEBAAAAAAAAAAAAAAABAgMEBQb/xAAyEQACAQMDAwIEBAYDAAAAAAAAAQIDESEEEjEFQVETYRQicYGRobHhFTJCUsHRBjPw/9oADAMBAAIRAxEAPwDq65VmAaR7Q7mNPlH/ADfMw9RsPbAvxR0wPln8MC1jYRpr0JqaqY8AEVse1nCDpnVTFLKuWAzMjBQY4AVgjcXqkZ28q6trVfTud8ax9NeY+LnZBmYgfNDCGEaH94Af3wH4ua59cLL4Dg9nviDWipHHHmDH8zEgQRFQKZpmHmZaNlCOcAQ5mD7TDHnZBOHBKkOq5eMiyAIhsR6MxN3ix/F2UgbKaRIsUWkUVrTpJHHajx+eOIzlVZlUhlDEBqrUAdjXuN8ZVaji8HtdK6dS1UZObeMWt7Yd/Z9j6L6b1PLy2kEsb6KtUZTpHbYccYYY5V+x6XL65ADJ9oKksCPJoDLVe9nv6nHUZJ1UFmYADkk0B+eLhLdG55+s06oVnTjey84JMQZqRFHnqu3qT6Ack+wwLJmpJB90ukf7xx29UTk/nQ+uJ8tlFXzC2Y/jY2T+fYewoYo5RcvT2dtUdwAH2LP9VNhR/X6YImzLRV4gBXYBl5J9NHJPst41zPVh4TPHemiFlIGgNuAWF6tIbk1iCNHPhlA2oFS3iAsLvzHUTpG10U9u22JcEx3GME6uLUg/6g0RXYg7Vhd1zqGWiWszp0HamXUDftR9MH5PI6B5mLmyd9huxOwHezzzjnX7SRIDpLpp8p07l7339h9cZyi4q6OvRUY6itGnJ2TYyzmQ6dJF9phd4gNREkOoEaTudBF7ewG2IMr06SRfEik8SN7uaFRTEHfxIDuG9WjIJ7jHPkncKUDsEPKgmj+XGOmfs0mm+zqhiUQjWVkD2S2s2CvI5O/8OMlUbPU6h0mOmpOalfOOOP8AYjzb56IFtTEhh50NqgHdgFLKfXUi99zeNX61DmG/2mFWtgizRnwn9yH1DWBsTsNm9rx0zM5VJPmG/ZgSGH0Ybj8jiudY+E0kJag5IqyfDk/KVRvt2dT9canhFYkyL6RJlJ1nT/dTAJIB2AJrVfY174U5n4mCISEJZaJW9v3SQwsE2f8A0w46z0mWGJkJodg33ZJBGncXG5rawwNUKGObdRjQMdBse/8AqP8Azet6vEydj0+naKGok9zeLdsP7l36D12MJmGVwMxKUiiBYWsdEtp/Pb6kHHvh4fcgWAQSG9RbnVe3J8v9MV34HyeWllKTSNHKSDEwXUtBX8QMO23B9sXrMdNZpGlyhDuP72EkDxP3JkHBLKASKG94adzn1lBUKmxX+/8Ag2Kg8itrqjydgPoAMDwwHMTLlw1Ju0jj8MQHmY+hY7D63j0WfRtQ3Rl5VxTKdybDb3XcAjHszJ9myKlRUubJdyORCvyqD22IH5nDOQk6l8TCngy8QVaCZdlNaRtZqqDHm7sDmsLMvmYYlSN2Wv4gaNbn1G7E8+uK5O7+VyaJB9QQOODxfOx74ilLEjWWJGw1Xx+eM5VLYR9Bouh+tBVJy+V9lyW+Pxcoxmyzaa3eL8D189LdAgVxjo/S+orPGrr+IA17EWPqP/PXFBzMoRCxNLW/qQDZP67YkTqkuUy2Xghr7QYiz6htEjPqQt2vegD68Ys8CSsy8Zvo0Ml2lE/iQ6Te++3J3Js9zeIU6a8Z1B3l0kGONmIC2Tq3umIU0L9Pe8UJlnkOqbMzyHewHKCz8oCrVD87xpPBmEI8DMzp/NKxF82Q34R64LMkuOU6hLF5c2rMNvMQCNW1kbBQov1J2J2A33jymVzOponKszWa2YHsQri1sDaq23GKz034qzqSeAyLnCOQilXC1y1DRvfff9cNcj8Q5CSQBgctMrk6ZFCef8X8FnjfffBdoB7lemNGQUnkPNiQlwffkG/zPODsvBpsklmbkn24AA2AH/l4lVgRY3B4I4xnCQzxxgnGcewAYxlcYxnDA8ThT8RtUNntJEf/AMq4anC34hi1ZaWhZClgPdfMP8sNciZz3NRHTm28IFVklJfxypFSSf4X4vofTAnxVp/tCZm2Fqe240L273gnqkI+0ZtP9mUOGcGYEsdaqwKnt85r6Y160imSOc7+Jlo3X66dJ4/83xS5YvAB9tb8MZA7CjsOw5x7EDrGCQxNjmgtX3rGcMLHR+sTxfZ4xlSskLKUMCLaSIaDU6jyOvIsjuD6jm/V+uzAywElipMYlLHXpUjSL7AVwtX3x2fM5WNIn8NFWxvpUC6G11zjh3VsveYmplvU7Vv2sntXA9cTXbSwe50HT0qtSXqK9kmvZhmX6/NJEMvSiPTpcs17lSCwuqsjURvuCR3wigkFLqFlW1CgKIIFqeK45HqcO/hro8k86xKV8yhnBsgJVqTtR+YcG98XBf2bZcUjZt/Eq68g/RDbf1xgoznk92dbRaSThxfNkm/uylfDfV5soXniVC2yNa8qdRN6a7qu+DIviyUZpM0U1MqsuksdJu6oVtV+/HOGGf8AhN8pIqlwUkkSNZaI8r6lYaeNQsc2N/rTxf2ZRf8A+l/pSfrioxnwjGrqOm39SedysnZ5VrPgr/WfjDOsF+98InUCqVYoit9yOa7bg4J6r8fTNoOWLxkbsGCkE1xvd773th7mP2dRN82YYNZJbQgJuua2/P3OEfw38HRZozjxnXwpWjFBTqrvXbDaqXt5MoVemSgpuK+X2ds4zjIL1j4knYyK6rpextahyNtRo7muaq9rvbEnRfjCeKFIVEaRx0rObuiSfX5qv9MWKb9nsLUBOyi9lAQCzQ+pNAfpivdV+GHyU0f3oaN22aiLCmyrci6Hfb+uFJVFlhRn06rH00lfm1mr2/Yx0341zC+IJpXYNVFQtrV2FGwW7G+52/PCsZx5ldG8/mDKprUSWqywosQCRZvnHujdL8eaKJiR4hNla2VV1Fr3Fdh9MWP4h+EFycJmjkLkMtmRRS77EUPU/wBBhLfKPsdNT4KhXSikpytbGP2KgM0BtpOnSUIv1a74qwfbBy9VligVI28IhrGkjWVYFrJu+Tfb5hg34Y+HkzbsJJSo0hwVoE25U3rHFg4tY/Z/CyaftErAEUfIaoHYEKaG/HsMEacmroWr12khLZPs7u6b/Yquc+NswyIEdkdSLYVuACKN3qv3HbE2b+Ncz4YEiK6lV84tTqZATupruew49sFde+C4IIJJY8wzsosKSm/mA32HY3hhlvgKF4I3MrpqRWIXwwC2j102eTXPOHsnexyur06MFLbi77O98MqmQ+K50y6ZZUVtN+ZyTtqJqjQAF1uaxXM+yhpFoVr8pCjgE/TkV+mOnr+z7LKbGak3FfNF32IorvijSdEMmZeNBdylECFdxbW3oAqrZ+vbCcZLk6dPX0k3J08d28rv7ifoco+1IzMsaBi2plBC0CVB3F70OcdDnjZAzNGXdVNzRO221oXQjWlkAatJXvfoX0z4CyeV0vmZRI9HyuVWM7b0DuaG932xaOt5jLxoHlXZVtWjvUq/wlTdfQ1Qxai1yfP9RqQr1Y+ld9uOc9jmHXeuNmkBbQxU+SSgH0EcHseeRt5TsMAZjNgrHTyMUWgjhSqbgnT6qSNlI2v9bvkukZXqDyeFIdAUEkRhXDE7XsAQaO+m/fFSj6Gxn+z2fEDKpri2am9/KPNft9MRPdyuD1OnU9K6eytG0oZd158+UDSzEeHJ4YO16t61B2oHeuFG2BnzQIUMpOkVZY+pJNc9/Xti3/F3QIMqsUMbnXIw2YC2A2BOlLvU1CyBzin+AKBLgWSBYP4avcDbn+h4xEk07M9nSVaNWlugrLNuVjPH5l7bp6xv4uckRQvnGWjbUzafMC7GgB3rYepwo6ZmfFeeZr1tO7G+NhUansa1Hb6emM5jLH+7SgBRsMwJaidyE7GxYIJ7isZmy7IPHiXWAFM8V23HlmUVdEbMCNiLqqx0o+Bl/MFjnTte/wCVbs3rtx7WcRZ3OlEpfM76QqdyS3kWvUk7nGcvKkigq4N0LBu7FuebvgWeMEfC8IkzEuZkHkyoGledUpXb8wK29SMBAyycsHT0GXkZi5HiZmRVJ1OdwhYCwDY37ChtqxV+mjxxLNKP71i1N6AaVH5nb127416zM8svgk00h1y0bAABs87NRYEAV8uGUa6ABp9DXoAKjW6+n64aAjyBzWRI+znXGauF7IOwJ0n8Pp/ni/8AQetx5uIPHsfxIeVI5B/Pvilxr5gOSGUWQeQCWPHvj3wpP4eazAFaAizn2OoK1/zJufcA9sTJYuM6KRhN1jOZlJB4UZZBQOwNknc/MDsO231PGHSsCLBBHqMBdTyZlCgMBpbXRFgsAdOoWDQJDV6gYlWYCeH4wj0hpUKhmKrTAmxudYNaSFonfvXO2HGW6xBJWiRSTVA7XqvSBfJIUmvbFZzHTc1EptVzGryk/OwQrT7vvbmroEALwOcCMgeaKJ10SWoYWWDSMi67SgSqqgWy/G4vDsCZfsaSpYI9RWFHSchmIpWMklx0aHiM3mJFUGFKoFgAb8bnDi8IDm8szxTZdi8aAp4bOy2PEhZ4m227Ed96ws6wvi5aNlYOcvK8BN6QVbzxtXYYsPxhlRcihRaMuZTy2KakmJHHlNOfrhaJVlZk8SOVsxFZCIqhJYfOi1Zskalv2xfe4ioeC3pfv6++PYcoqEA1z/J/rjGLuFvcvYilii0CTQjxh/s4XVLExFsiEmljs8sPLwDxXMOolhmJCthtbbDc73Y99sdvmhWOFlCgbc/ve5PJPreOK9Xm++nAUC2ZSRd1qv1rt6Yzr8I+h/43f1JrnC/Utn7KpazE6vfiFFoEb0pNivzXCn4m+Gc6mYll8KRw0hdZE8xom148wI2HG1bYi+G4MxNMZMslNGoBZSbW0KrVmu3FdsNcl+0TMRjTOqyMCQwoq21V5gaBu72xmmnG0j0a0K8NVOpQUZNpKUW8r6A/xH8UNm8qsTx6XjKFySdWoah8ukDfnna8a/s51nPRc6Qr/ppP9LP0vFl+MxFmenDNonmKo+/Om6KsQbOnUTz2xVP2c5onqEQoAeG6Cuwot3J7/wBMNpqau/BnTlCegqqELWvdPNnbNgz9p2n7W+oebw49HPFm77V83vYw7/ZH/cz1/vB/+gwg/alN/tY01egDcAmuQdxxbH9MWP8AZYylMwy7AyAgcVajagaxUf8AtMdQmukx97fqig5npuYMvlik1HSEKo4ryqF3I2IFCyeQcdH/AGgPWXhU6TIWsXxtFJrYVvwTx6jEvw98XnMZgwPGEHm8Ng4YOVO442Ok6qxVfi0zjqFSDUoIKE3QiY0aFhb5B7kj6YLKMW1m5CnUr6iEakVHYr/UO/ZhkFOYmmUeVFVF9NTgF6vfbTt7Nh7m83/aGQzQTchpEAHfw2LJ/wBShf1xJ8AZFo8ip8oeTU9gUN9kNDnygH88T/Cvw8+SEgaUSK5BAWPTRAI33N7UPyxpGL2pHBq9RGVedRvMWtvvbn/Zx8KxT8RT865/TnHTP2ZAjKPsK1tx/KLv3/7Vijdej8DMyxaRSWgG/wAhbWu/5/8AbF5/Zoh+zS2ALewATwY0q9+4xlTxI9rq89+jUrYbTX3OV5WNbXVsu1kDeu5x1nrnTJMx0yKOFQzVE1WAKAFkFiP6745jmM2DegcsW3UcEClrcbeuOodV6pJlumQyxEB9EY3AIogXthU7WlfwLqvqOdBwWb4T4v7lJT4LzwZZDCG0kHeROxuvmw7/AGXdOTxpZRuVRVG4NMxYtRAo7Ku/ucLIvjjPHYOh2skoo27k8AemHn7M8yPFzMYqtQdaoWoLD8+V59cFPZuVjPXfFrTT9Xbwv5b8Xzcq3Wc79ozUzk3IHdE1XQjVWAArbgEm/wB7BPQfiCSCBoWy/jLTAEkjQrL5lHlO3J/M41mkOWz8hKL93I7qtKDV2AGrurc46F8N9bbOJMxiMYQ6NyDZolh8o4sfrgiryebMNXUVKjBempQtF82t9O5zz4G6qctMrEgRu2hySe4FbV2NG/c46Cvw4B1E5vbT4fH/ABPlJ/6P88cmDAREFUGrSyirP4gTTX2PPtjp/wDasn9kePf3nhfMf3r0k1+pw6Ulaz7ZI6rSmqiqU8b/AJH73KH8adSOYzHiBgUFpHQ7KaJ97ayPrhDmQ9/earr8d3X571i5/Ci5QpHJOI9cTbETKpDa2KjwrAra7PPpW+LNmvhnJ5mXVKXZqrwyxXayeNmN361jNxbybR6rR0r+HcHaN1fv9iv5/LZxASsiuSFUK4Eb6RWw1DQ+wHyte3rhNJn8xHJeqRJI08odSGJutkrUw8xYgsRtXGOvtGCNJAI4oixXuDhZmuhRsCq0FP8AhsA8f/222H/KVxspI+TlmRQY81lMy3+0Rvl8yauXLjnUNtcQs2RyKP1xNB07NwwTpl/CzkUxEmuNqcN5SCU22IA2GGWe+CypLRAp3pPMhOkrvEzBhQOwDtR7YS5/K5hXGhGOkN/cyMXQEWSIyEkB1AAeUhQTzQp8kizKCHLnS7FZD83iKyW13pIYABQRd9/0w1WRfm1KRzd7+7H0NAge4xjK/FmaKhJEjmGklhKl6KrUjHmxuRfNqOTgjIZ3KzA3kMpqs6RpHnGnVamgLrkHijzWHcAGfqS3oi1SyEkKiDU9bbmhsTySarDKZFyOTkWcr9pzP94ovyR8abF0Alj6nawLxHmviCdaiy6Q5cOaBjjBJH5XTUHrb8HI7V/qtGY6mlDeWz+I38kgBC3pfyk9x63hAPcj03wqlXNfZlIHkR2J1EAjY2Gvn5W55wRlPizNISG0ToBdkaWq+bUVXuVr3wl6jW3yD+aTTWo0v4iCj6SpJvcA9gMRZNmDGwbAYUCrPY5Hk+YoSu+k2rb+ysMvcHxrAR96jw71Z0sN+N42bb3IGLDk545VDxsjjsykHb6jHHs7nTI1WtAABUIYKBfK6mb3orWGufih8GOcXDJa3o1BiCDwykNfeia2wrCOpMcYBxzvoXW8xrMcWaE/JVZkayoH71hhvtfm9d8PoPizSdOYgeNuD4f3oB7XQDi+3l7YRQd8QxgoJNOrwydYrcxEVKv/AEm/+UYogmkiLwNOAYG8SFdFl2WilNpOzKOw7t746Jk+oQzWI5Uf1UHcfVTuPzGKR1zKSZZgY9euHZWNMDljdEj/AIbWp9jZ5xSd0SQ/ZenS/esShfzlNK+UtuV57XX5Y9gX7Hkn8zZrdtz/ALOeTueBX6Y9h+pL+78h7V4OnCXx4mWtMi7Mt7q317g8g9wcct698KzRtNLKyKvmcbkkjfsBtZ25747GSALNAAbk4VzzmehAoI/3zDyj+UbeJft5ffti5wUlY69Frqmkm5Q78/QovwRlJ8ppzEgT7PMgs6vMO6ECtzufKN/Nhx1v4Ly00onCyAO1uFOkEk7mili+TRGLTk8ooOpraUbFm5r+EcKp9B+eEeYyUkGaR4Yyw8NzLPLJS20gPnPfSoOkAem4GEqaS2jrdSrTqutF7W8Y8dri/wCMAzwJk8tpiQCy8nkTw0oUpO5Nld631bHmqd8JwNlOpKJAbjRiwAs+ZSAFAJ1XYIHPtjqPgeM6uq6yurTmJUFqGI2iWhfA85FUBu2AOpZ1IXlhLGJ2jATNuQfvG1ER7il2FgCgd+/KlBXTKpdRqU6UqSWJJ383fdson7RGEubUptaLd0GUbDcXdCidvU4cfs46vlstBL4soTU9jUbsAVew/p/ripfF+Xoam0mQKoanJKEGiCGcuyknZyq8itjhBkJV1efjf9a2vvV1eMZS2zufQaamtVoo0pNpJL64yWNJGjzQaJlBRzJGADVVqo0vdRRO9jF0+K+pZTNpFplVZAUJVhdxsVLqRR3Hzf8AKfXHN8yx8T3paoHjQumu+wofliXKqwcbb77E1tpazZ9BZv2xCm0mvJ3VdBGpsqbrOKxa35nQPi74gy7ZaKDKy2NSKdBK0qjbcjYXWEvwt1QwTlpZSYx82pibS9JOmyR84PHbFYnRQ1Lxt3vehYvvRsX7YIgA8NtNBtJ1WD8upa0nj057jDdRuVxQ6bTp0HTTbUubrOf0LH8cyw5jMJNA4YOhDGiADHdncem3/Lhr8CdZgy+XkWadRqc6L1cBF2Fj1PHviixZSVkZ1VjGPmI+W/8ALEhUGEatq1aN+Ta3a16d77DDVRqW4VTQ05UFp3NtJpdrrxf/ANwatlBpbS1lWC3Yo7GyPQDjfm8dJy3WMhJloMvOwc6I/IQ9agBVED1H9McuhC6l1fLYuua71gnM/g4rQKq+NTfvb83/AExMZ7bmmr0K1DjGUmrZTWM2OiRTdHUikUath5X3s1RB98VZc+Mtm5MzBRjVyFVSKdGO6j0qr9iBhPFE6utqQxZSNYIvcUd96vEU6rfk4rfnne6sXVVz74JVL9rGNHp0Yyac3JSVnd3VrnTeoS9LzbCSVkEoBBDNpbSObB2YAWQd9uMSZX4ryiERR+WEK4DhW3caSa24pj5jyf68zyQWjVeJ5qu606DdVtfPPqMQojHYAkeg7n/XFes+yOf+DUsxnUe1cXeF9P8AAx6b06WeN0hHildNKPwi2J+YADj17nFwJY9MOVjAlk8MKREdZWQtqKtpsLW4smtvfFf+CszMhdolJQFfEPKgEMAXApmUXdD1xcemZLw1y08jCIJYSPwyZdJBHghrt04YApqAAvcXi6UE1c87q+qnTrqni0WmvN7LmxH8L/Do+xaZIlTMGOWIkgXTk1qq720n/wB8GZaBmAjfRNJEukvusMWyiuSXcab9RZ3S6ww8F5yfE+6HaIN5mXt4pU7A/ur+ZN1gqTIRSxrEyAKhVvDGwBU2AQNivtwcbpJKx4NWrKpNzly3cUdVSbK5fxIpw5DLfj1o0s4BOoeZVAPNmgN7xH0f4n8WNpJIJIwhpzyF8oNkbMFo2Gqqxtkkkh1RSFQgdzDBFvI6mUupO9KlHTpIoDkjjBHUYZTGZZU8VgQVy6WUW2FlvxSlQdXFeXZbo4lwTJTGGSzscyh4nV1O4KkEf0x7M5KOStaBq4JG6/ytyp9wcUx0ObkEoWTW0ZrwHKMqo7BZYi5VHVrGpH3G3OHMM2aREQETZjSviRH5VaheqUbIO9GyewOIcGh3Rv1D4YjkOrZm/wCILPIO0i6ZRxyXP0xWeo/CjQlZIy6aLK0PFjBuwTpVZB5jfytybO+Lqmfo6ZUeNvcWv5ONq+tYNjYEWCCPUHC3NchY5rKHVdbw632Vp4W1o2x3lUeZe1gqOSfW0eRPiPYeS1JYr84p9yAQrABZB+6OfYY69mOnRu2tkGsfjGzf9Qo/1wp6j8MJKbNMf+IPN+UqaZPzJbBcDmXUMyjO3ioSqkQsSSCYm3jaiDsGF+3lGJszoiAWQiNjy7MzAupK8LTMAFHnPlJPB+YWKb4TdASpmiIXSN/GjoVp+QB7WhRKGqF3hPncrMWYurMCR54LeRb5EjtToO+6D2xQEGTjY2ZAVKG9ZPh3ybZENaTXzXuD3xDmQ0/ygUBYjlXSTtuy2GZvW10843nzCBQIpCpQbFdgQdvNIQfD4LFSCbYj3M2Si8OPxF0+I25YK9EE+tazQF3S7gcDBcLGmYn+zppjLURYkDbg/wAAOo1Q4ry2bN8R5bLBVLzOQXAZFB0s55OsWPb8IPm57YP6RkArmSRSF/4d7mvxUdRNcA67J2bbG0joGFRSPepiHBARB8oC6iLF8bfMNsACzPZuSaRXCBXUAKgUWAdhplGlwPckAevbBuR6hm2ljVp43H3lCQlgF00weSgSrfKCCQSNuLwNL1fUUTwkW9RIprCVuNPJPlbzEJxVVgzoZjKmUsIpEUAEEUCbAVTWk7EHSt7sN7GBoRH/AGZm/wDDjTR+CpnI0/ho6dxWPYnOey345zq/FZUG+9i9t8Yw8iLx1mVovDaZfGMkgVV38KM1Y8qgs7GqBINn93AGckzEksk2Udya8NUaI3HIo3Rg7KqIwKksNz60MOp8wWiRZ10NICGhUkux/wCGysKHfUex3IwBmSuWjVWUxo7ELDG+7MQSTLOxAGws7jjk8Y0sIbPnSQFRdco+bSaRG7hnO1X2Ft7Y2y+S1HVOfEcGwv4E9NK9/wCc2fpwK1m+uaSTBMqRBR9n0IHikZfniYqCwe+KI2IO++LLNnAqxu4KysBpjAtiSLKV3rueBV3gAWyxzQ5iM3NOz+KSFJCabXQpBOhNOr5tidJ5JrBEuXjkdyYY5pDSk15VUfKrubGoG/lF8bYJWKScHxToTgxKdz7O4/yX9SML8/nzlJIvEkjjgZ2RYlT8AjJFUNWvUANI2o8d8ACfr3TY80zQ0ZJnSTS2jTGpSrRCeSCR5t9+4qgu+HeiRLGuVeOOfysxJi0yRSA7rKDZo3Stf4drGLnnirtFJJqRkbVCF3dwyAMpQjbmiO1XYxB1TNF/upJUjZwdMCv5322Vn7X6LX1OE4q9zaNepGOxPF7nF88zRzyXHXmdV5+XdfWrCkf0wVkJ/MjUoCAKSSfl3HrVmz+pwdmuk5iYRSs6GNy6xKu+hlBYxFQoIYaSODuv0xrkekuEgm1ALK+gCjZZW+Qijua22r3xxyg74R9rR1mn+HTnNXwnnH0Ao5lplYL+HTSDsd+KO4w5+FkjmmkSREKspCqWWPcuukFxRoV2s8bYm6d8Hyssr5hXiEYutIN7Ekg8ECu3rgn4N6MviTnMxjw40BYt8yEMGUhB5xagnjtWHCErq6I1ms0/ozUJ5SXHvbj3HbdEy8SrAI2zBkDyBY5AFVRpV/C1v5mqu987gbYY5D4dyjQo+XjBXcjXZYHYN824YaaI/h/XSTpkasWkATLsxeJEZi5mZgQ8QVFdDpsFQTzvwbImdYEjjYPCkjFUhTzSyMxLOWkulJssaN8+btjr2x8Hxz1NbPzPOXl5fuJundFjjmaKkzJcu/AVVJqlc71QF0ATvsuCB8OZMZlIpWIn8rqEGlAASVjANj8JNHciz9LBPlAqI+W06Y91RaKkcHTXt27/AFwF1LqWXmjKSow1Udtm1DdSrc2DuDhbI2tYp62vuct7TfjH5CX4s6Eusyxs8soctoNFVJ0lVbzqUFDbn1+tFnzFNKj9yQCoXYh+a2u65xfuvTHM6IgANeyhq1SFQD5mPcc0N/ftinS/C8waQzI0MYsoaLBvNQUMoYg16jGNWDbVke30bV04Ql608K1l4z2B+n5+NpmMzKkZDVY4vYLYBYbEjbHTukfDGXWBlRQWcXbi9JKjgXdUBwd6GKH8J9AzGXzccuYgIiOpQG0FntTpCx2TfF8AC7IrHTJMm8/97SLQ+6Bux6SsDuP4V2924w6MbLJj1nVxlUUaMvlsr2ePw8iTonRocqGeEIz2S2YYlYoxuKUFjdAkUD7FhgvqRaCBZ4G8ZyR4mY0iRvDoklVBC6dQApSAASe2HkuWjlQIy7IynTxRQhl47WAfTCOASwXHIQFaWRooob8R7lMllrCooBog7Udz2xulY8SdSVSW6bu/LFseWad55cv4SnWr+MXB8N9K6mSRQRJGVFNGxFG+xFWOSeSf/wCHpRVfaCNvfw1/H9T5ePmxHnMrM66nRZCCNEAaowb2aRiLejvxXopIvCmbKy5hhK41vEWieOKQoY3DErJESaJKlbD8ivcEJH+VySKjoLV2BDPdu1itWvk+3pxQ4wqXJNlZXEKrHA0aa5iwAVgZAzBaOqQ2m5FGt74wZFnXWOOOUa80VvQhAPNB2PyqK5PF2ADsMTQZRmdXzBDOLKKopFPcqDuWo1qO/oBZwCBclkSRUYMKmtc2hUlmrk6Qo0X3YgHmgNjhTn8y4SNVily6BwfDBZPGD2ApmX5JdVGmbzEgFt8M+oQSwzpOnjTM8jKyKdhGYyVXSWCAK6g6+d+949PEHlBMay5hKIAZhHDtY1m6Yg2QQurcbDnAM36bm3SFvtWvyvpiZgBJIKseRbOsbrtzpuheNYsm7uskgMCj5QhAZye8pXyj2QWL5PYA53PeE89a3zMcVtOVBSEOCVKxatWgEebSGO25NYm6Dn5mkETiV0ZCWMqL5D2IlQCORH7AeYd/QDXkBv4EycFZBX4vK1/UAqf0GIR1VAypIDHIRq0NRIHqSpIA97xr9sdiyZemRTpaVtwhHKoP8Qj9BxZIobv0srC4y8hWVyCZ2pmJsWx2o+UUFoAcChjN012HcKSQMLBBHsbxBmMhHIbdAWGwbhh9GHmH5HCydzEryZhEiaOi2YU0kiXvSg6g9fgYEWRROIPh3rs2cdmhh/2UbLNI2lnYc0oBBH6YjY0O5v1D4bVzYKtXAlXUR/LKCJV+uo/TCTNfD2Yi1GKSVF38oqZN9z+5Ld96Y4u7SafmBX35H6j/AL4kBHIwm2uRnJs7HnSSdTyBVPyMzMNqGuNgGWudRjbfvhdBmpSI18R2Ykh6Osi2A+8ZqVK0k7Ljr+cyUcteIoat1O4Kn1VhRU+4OFHV/hhJhZ0uRx4oNj6SppkH5lvpgTQjnsWZEmzgFX12R5lVSRWtjQsmx5diRwcNcz08pGyKVYFixfkmlNAVsCrG+1VQ95M98KMh11IoUdwJUALWdJTS4N7kmNiOb9AupZTMFTJo8WNRYeKTxQNBDRggUw82xtRQA98VfIGkXTwVBePWxA1Pv5m7tz3O/wCePYS/bYX8zhA7bsPEApjuRVbb4xh3XlhZnXuo5WaJQcmq+ITqkaTcuqi9BY72x2B4XHppkzUCs8YWO7dpKUwsvBAdSCQ21nbvvjfI9Z8S41UNOmzgGkHbVr7qfQWexAxp1FJIlWQxtm5tWygaVUbliim1UgDYkkk0LxqQR5f7pJHRykZ+8lzMoGpyFAJVAFAAUDzEV/CecDSdRZJmXLiJiYhKrSEl8ypssEksBdPpuN+AMMc3m0zWWqIn77yA6C2hu4kUfKARRusLsn0qGLXCB9qJOrwmVfDifcsbIKxizekeYDscAdh/l86kkSZhCArIHtthpIve+PrgcZhswQYVCqvEzrZs8iNTR4/EdvQNhfNmgjr4itmW0+KqRBfDjjBq0UsNZHru3oBxhxlc2sqLNCdSsPpf5HhhxR+mHyAszGe+yzgGJjEQBJmmNlXYnSGNfL9Nl1dsY6t0aOZptJKlwpkL6/Cah5W2IBZavZhwLwxzOeQ3GF8ViKMYF7Ef4hPlQfzc9gcA5meOJ4lzbi5DUcYH3Saaq/XcgBm2sigMAGctk1Zg0EaatIDZp0FsaAJUba2I5fjf8XGB8jnso7yxSVqLeFqmYa5SCQyhdioDcCgDyB3xJ8RZSRp4XQ6goNoNBkWiCJIlc6bvykkbA7YhTJDSv2jW0zEO8MYQeIVb7t5QooGlUE6gu1YQDTLZhoXEMpJU/wB1K34v4HP74A5PzD3vGjSCWQPl0UtpKnMEHSFu9K8eJZ9PKPXsRpIUzTvDmHEhAswJehOK1uB5n7gEitiBteDchmnQiCY2dxHJ2kUdj6OByO/I7gMZr0R4XaRo5PFkRjG7t8wIO4AoBVNbBQAecLD0qWxBL/tWXlJcyNVwyAlrAJ80ZPA3rjcHB+YggiljZARKqlVjhAt49/Ky8aQdwxoA998eyvTxJayFAiUBloyNCWLqQD5ibuq0+x5wgAunAQrIIGBDMXlzLgLEhoDyKKVjt+Ha7tr2wxyGVjUeNG3jFvmksMSPVdPlH8qgX9eVfVYpyXSbaCR1EDItmB0YeEzgcozAH24PO03T42y0rtIIw0qioYLPiOpOqXSQAlggHttu2AQ16h0uDMAGRA1AhWsgrZBtSPlNqDY3FYByzMSvhMZGVTG2af5avgKCBI11uBXv+HEv2J5CWmHkv+4U7e5cjZz6p8v1O+CuodNjzMYja/DtWpTV6flFjcUaO1bqMAAXUMvND58uEd9JLyTG2NVSDdQgbc2KA08G8JMvlZZGeXLqqN4xLSM4DRmwZEYi1mjI438vG1bOE8SNBFmX8Vgx8NUotNHRAEqEUKvc2FsAki6xrm4GWMNJDrUFVjy0XyC2rU5ApgLs7aVA2BO+AYU+YeZv9noKBvORYPtGvD/zHyjte+JspkUCuq6hIwp3JuSyD5tXf1HYdgOMV3NPmHlE6N4ekNEUppTG6MQTFGtB1awCxFqF7dnn28iOLxFrMsgPhJRbVQ1d6C3ySaHrdYBC7L5WTLZho4UIgaKMvM70qlTIHbe9UhGn0G1n3MygeQkxHSGCh8yUGqUrt5F4/wCcgj0BG4lgyjStqzOkkG1iBtFrhtwPEa/xEUOwHJX9Tmly08cryyOskrp4aAsPD8MmNQg/HqHze5s1wALZuuKVEWXSRCz6gQ665wraXBeyySHtrq6C2L2ddIzrFZROzGJKKzSoYiQbtWBAtloecAA6vXGuaEbODJGTLaukCNuR5SGnAOjyuPmYkbCrO2Is7m1Rz47q+YABjSm8KJnsRajVWWFa2o+gW8Aw0SyTGraKHs5Gl5PpY+7HayNTdtPJj6tkZEjYwSmJI43YIii2lHmDMSDY2Njve5wD0TrMpkSGdvEaQG0KBJImCknWosNGa2kG11zeG2Y6hoYxxqZX9BxH/wDMb8I7929jgAXvJFmVSeRWjUCll8ySPqC0I9J1lSbGkjehQ74xLpSKNJVaKDaOLLJbO9AkBypv5VJ0A9tyeMGydPkCF1ZZMyRSu3yoCRfhruFAW67mhZOF0eb8QT5OfTmZUJABTTaGNWVnIUrH8xUN3K7YAA36+6anjePwhTQxmOlki0DyK43SYNY0N7bdwy698SRZRQd2lcAplx8zFuNgCV9/8rwgXPMrvB0sGechUkzDafDiVL0rqVQGYajvRJ96oWH4b+Fo8t97IfGzLbvO+5s9lvgdv/KwgFXT/hyfOOs/UzsDqjyo+RfQvXJ9j+fpi7RxhQAAABwAKA/LHmcDkgYGl6lCvMqD/mGJc4rljUW+EF4FkyaNvVH1U0f6f98CSfEGXH+KD9LOBZfivLjgsfov+uMpaqguZL8TSNCq+IsYvlnX5WDezbH/AKh/pgQ9RRW0OdDgaiD2F0CWGwv3rCXqfxZqQrDqRj+OgSB30g2L9zdemBIPiPwlKxRAXuzMSzMe5Yndj9cck9dplxI3jo67/pLepvcYGzWQic6mQav31tWH0dab+uKbJ1yXlaT+UV/5+eIZOsznmVvyNf5Y5JdUpLi7N10+o+bIuX9kj/fTf9f/APGPYpH9ozf7x/8AqP8Arj2J/i8f7WV/DJ/3I6Bnsjao0VJJH/d7UKrdCB+Ej9Nj2xpD1qMrb6lcHSYqt9XNBRZbbextW+NVzzzGoFpP9848p/8Alry/1NL9cQT9LMTfaIreYCmLmzIn7vFLXIoAfrj6E8ki6lL4cUksoOXhu2WOvFcmh5mXgn0Wz/F2wVNlcvNBGA1QWpARtKuDwreoJPHc84Ky8keYRH0hheoBhurDbjswNjCrLRRprjy6h016yXowxMDvpPcg76Rwe64LdgIIOnouXjXMaoGy7FIpENMRwuirJ1DbTW9cYlDLDGkWp4ImJCjdp5WY2xAAJWySSaLb/hrBGcjZYXnib7ROFJRjTAevhqPKDV1W52BJwlQ/a3RBL4pCGWN78N0OyvDP4Y2Vr7AHykYO4FsgywhFRrS91733a+ST3vc4x1BYTGxm0eGVpmaq0ntfpdfnWFXSwcupgQeLIWLaFJ8OEEDyl2sgd65N7ADBseQ0kPM3iUbAqkjPqi/92sjsRgYAv2oRRoGk8CIAIJpyBI3oFDcfVhe3y98a9WLQLH4LMkJJM0ygO42Gl2LXa86m3PHAwZ13IiSN3VA8qxyLGDxbD0O1kgb4X5BpI0ijk3lUKFghJpV06fvmOxG12QB6asAC5em/aY5Jy0alrV5dTCO1AAzMSjbUVA3J/DV1y5zStmU8NFKxGvvmFNY4aNebvcOa9gcbZ3LuFVnXxnLKqxCxEln5mABLBedTA+wGFWbaWapWSQ2GQLC51RSRuw8WMbB1Y7Gx2HY4QDTodQsYJP7w2wlP+MByxJ31Dgr22rbjbMdG0SnMwyaJC1yaz5HTYUw7aQPKe2As47eBEs6scxQYLFWsN+8Oy+5O3bfBXTbmOjNEF1oiNdkYfvVy+/IOwI474YE2Wkd9QgLaGYt40m437RLyR6E0v82AZlaJjEsb6JGWJ8wZD4rNIpOpDW4T6iuw2wZ1PL5gSeNDJYRQBB2cWS9ns1VpI4rfY4i60EkVRMGYNpdIEsSlxd2Q1aaIvgCtzhDAvh2LMQtHEpV4F1+JJpUIRvTq+oszlvnva74rdn9rkkJOWH3Z5lYbH1aJeXPuaU8i+67OyRwqiZhDpCllyuXUlEjUjU0lVrAvf8P8J5wOvX5Y3JeRCRIB9nKgaoWYCOTLsN2OkgkG9ww22wCHy9OAhkWFqeRSPFYksWIIDM3Jq9htXArEWQXMxK0LnXpW48w/BHpKAQdQ9RsRvsbwT1DNLBvuxY7RKLZz3KD+p7dziIZVpxqnIKHiJTa/8521n22X2POAADJ5ZA0kuXAGss75mTcAHdvBU8ryb2Xe/NgM9WRUH2V2HmBzErxlpNDIWSQq1FkJIsj5VOwHZlk+qSpKYszEER2cQso20qSAknZSVAIPBBrkYglijeVJm8pRrWNUS7AKC5BuVKm9P0HasJyS5KSbCuh9T+0RuWKXE2nxY2uNvKG1Ix7Ud1PBsWcbx52SfaGlX/fEWGHFxKef5jsLFahgDNzeIQCB4Y4jryk+rj8X0O3secYnzrEWWND02r6YylXijRUpMJzUc2XoZYRaAAztKxLyNqGq2JFeSzqN8AVWAs/m4MwYnYCJ0ZrZhGxFAroI31owYkUewOEOaz7ve9D/AM5OBMeLX61Z2hG/1PQp9NuvmZZYOpRQxrl4ZGWJBXiAAubJJCCtKjfkjbgDvjEPxIsRCRxDwR7nWT3Yngknm7J5vFaIx4Ljkl1iu+LL7HQunUVzcs5+K6vRFsexbv7UNsJ+t9UbMoUYaUb5ghK6tqAYjcj2wBpxkJjGXUtTL+r8DaOjoR7BOS6nJBGIoajQcBQP1J5J9zjEnU5m5kf/AKjgfR7YyIzjnepryw5P8TRUqUeEjV5WPJJ+pxpifwTjP2ZvTEWqMq8VwDYzWCRlG74wcuByw/XB6U/A96BaxkYlfwxy4xC2aiH4sP0JhvPYxWI36lEOxP54gk60g/AMWtO+7FuDKx7C/wDt8fujHsP4dhuOyA4HzWdWOgbZj8qKLZvoPT3NAeuBDmJZtoh4aH/Fcbkf8NDv/wAzUPY4KyWRSIHTdn5mY2zH+Jjuf+2PuT5UR5zJShjOy/dMbly6Mflr52IPmavmUbEfvEYdS5SKeJV2MXlYBdgQpBUUNtOw2wWDitNn2hdvAAbLsfM5vRC5NEgj5l7kLsD3HZewEPSVliWXxVGXieWQgg/eMS3kEKLdDSOKJN8DnDdkeTUzaoYqshdpXoblyPlFdh5vUjjEkeQ0BnB8WYg07nuRsq7Ui32H53gPok+YjJy+YuRwpdJgDpcd1Y15WBNe43wewCnMdQjzGXEMWmFmYNHHqYrIuzFHKCtRW7QMWw16JnpB4pnNZdQvhvJH4R3HmXQaNA0Bt+uIIMjEkgMcbPKp1GJZCYYpWHma6oHfiid9l3x5prWaaNhmZ4h5RR8JG7iLsWAuzZbtYusCAPWSSQgLrigP4yKkJ7BQd0X+JhfoBscS5rpXlSOFzEusNLpJ1yLRsa71aiaOq72wP0fqrSFYZkfW0evUwUBxYBtVY6DuPKf9cFy58Rt4dF3/AAqvNdtROy/ViLwNALehdSdoBp1HSTc0wOnSJD5dRIZmCfi4sc4xlctFEGeEGOPcvmJLLFSSai1b1Z9NPoDeDZcq2lpZh4hRSUhXdARuNvxte2o/kBjTJ9TE6SR5iErKq/eQ1q1CuU/fU9vfAADmcxsDlw+kSFZyRpk1aQVLtKNl9SRsCKxLAwmUufuyjkalIIDAfMj9xvv+YOI8tkFgdWamJ16I1j+8ZXN1J5iCE4s0B64m6oumMPMtqGVUy6EBAWYBfEPB3N/uj0POAZJlerSTkRJSuBbSV5St1qhB+e/U7C++17dQjly+hstEZWLXMWNsyAHYMT8xJGkcc8YHlz0TqyyH7NNCQRrI8h7FTdOjDY1yNsBN8fwqm6M0gsEJ8u3cMex54vAIcy5lczCfBUnxUkj8SgPCOkgrICQwOrsL3GA8izKgSKTxGBGrMyV4akAAiJdroCgFGn1JNjCL+25c4fMgii3JUMbc/wAfFj27++CZZ9tz7bnGFSqomsKe4cR5pYZPIuux95M727HsBtxfbygdhiOXrJs6dr5r/PCOTMj1xAMz9Mc8q0mbxpRQ3bOMTd/rviJpfU4A8UnHvEI5IGMsvkuyDhJiKTMDv/74COYH1+px77YByoIwDPSQgnyn8sejy19rwPmM8qKWPA3xXB19x6f+fTHmV9JTTx3O6jVnJFwGUo70Pzxh40HLKPzGKK/VHP4j+uIGzZPfGCpJdjo+5e2my45cYjfquXXg3iinMHEbSnFKkl2QW9y6ydfiHC4Gb4kA4UYqJfGNWK9Mfylnl+Jn7UMCS/EEh/EcIdWM6sVsFdDN+rOfxHA759j3OAycYvD9NBuCTmj64jM5xFj2HtQbjcyn1xrrONcevDsibm2rHsaXj2HYLn0ZgfOZ9Yqu2ZvlRd2b6D09Sdh3OAvtry7Q+WPvOw2/+mp+b+Y+X64MyWTSOytlm+Z2Opm+rensNhj6U+aIDk3m/vzpQ/4SHkf8Rhu38oofXnB5iXTo0jTWnTW1VVV6VjbC2fqJZtGXXW10zG9Ceupu5H7g39awMYOua+xkJMxMJ/umO5BHER7sa+XuarBIEs+5LQxHsNpGHuf8Mew83uOMe/sdGB8UmRyK1N+H+QDZN/TfYWTjHT8yyOYJTbDdHP8AiJ6/zDg/r3wAD/DnVon1QpH4Wgkov76aj94vrZsk4XJ0ZdXiQyBIRIZPEaRh4VPcixpQQKxuyT3w26m0WtPu/EnTeNVNEX+8w2Vf5tvY42y8SySETSLJIlExD5Yydx5eSf4m/IDCAGy0aU8mXjEEZsvNo87gWToUji7NsPoDd4k6DnsvKlR+Uv5gHYGSQdpGFlt+17ihxxgfNZdocxBM8jyanaM3sqB18lINhuKvc741zHSYIJI2LUFcyRQxoNRkIN7i2Yb8bAbdhgQFhSSjpb6g+o/1wu+0eM9wKvltTOy7AGiRH+/23+X68Y0+ytP/APEGhz4CkUPQueWPtsvsecMYm/CQAR2Hceo/0wwKvP8AFWVy5cLqkkvS7sVBJHqx7XwAKHpiiT9fWiDKo1KVk02TLe9yE7FvftjoPW/hTINpkljWPSfwitd/hIG5PfbfbCrLfs+yjvreNo0/DF4hth+85skfyj8z2E5GUHMddjPmfXI37zNfGMZPPNKx8NQg/eNkAew4vD/qvwhl1kzCrqjMdPHGW2kiCgsVZu4Niu22CV+FdI1+LGsGkMrsCCL7Mt/1/piJN2wiopdzXKZ0IAAeOT/3OJJeo++BP7GkYEqRp/CW8uoeoB3A+uFwjJLDupoja/8A298c7gzdSXYbfax+eNGzfvhU4obmvrjCkEWGBHreJ2FqQ1GevuP1xn7Rv7fXCnw++NghrBtHca/ah+8BjQ5lfW8KmU40KHnBYVyHqueLnSNlH9T64AJwXPlCdxzgQxkcjHnVYSUrs9GlOO3BgHGbxgKcYJA5YD88QoSfY0c0evHrxH48f74/LGj5yMC/Ma9BilQm+xDrR8k149eAz1NbAVSb9/642+1t2UDGi00yPiIhWPYXx55mNA+48vI9r5xITIfxHFrSPuyXqkGUfTGt+4H1OFuYBXksxPC3ufpj2X6YTTPuew7D/X64taRd2R8S+yGDSqOWGIjm0Hc/kMbL0/2xmTJBQS2wHrjRaWJD1EiBs+OynA8fVCx2Tb94nY/TBSdLaTdxpQ/Kp2LfX/TGmVIBdZV0aTWnSTpX1cgUAfXjFrTwS4MvXl5MfaW/dH9cew6/sQep/XHsV6NMfqy8nR+qyy5qOOby+FRRomjeTS+qtXhqPORVebYYadDzEsCMc1IxVmqFWQByAOBGg9eF5A5wr6RmmyqywKdR+0GNXbte+ogc/SxiwpAIW1kmSVtjI3IA7AAUB7D87x3LHB5zMS6pSvjN4MbGli1U8h/jIO38in6nthrFGqqFUBVGwAFAD2GOc/EfWZBIkUlO8UyssgGnbVVFd/1vHQczmdMZkq6UtV+g4vDWVcO5JNMqAszBVHJJoYVZ+F82oCXEoOpZiPPf8CndR6k8jt3xFmG0wtm5fvDGNaR/Kq7fnZ/iN+1Yj+G/iJsy8isgXSoYEE9+1f8AfC5HcP6FIFUxFQsqfOP3r/xLO7BvU/TtiDN9CAk8eEhJtepnN06n5kb+Ght6ViXrkVL44NSRAsp9R3UjuDgLp8pzyeJJ5Y7/ALodyP325YfwgAet4OQDDOXLfZT8xGqZiSi0K8in5j9KHqe2Ac5PLl2tI7GqMTZiUi3DMAQgHYX7AdhiLrvVpIM1GqEeGAoZK+bWxAN9q0/1w1zzLK5yrraPEWJvfmqH63eF2ugEc/SpI3fRCzTeIZIswtbqz2UlJN0BY7igKw9zufslIRrkHcHyof427fy8n+uIVlaWZsqGKJGBqYbuwI4Dfh9zufcYXZ3qRgmaKNVWKFFbSuxcua3Y3QHPFn1w14AY5GLSdch1zcFiNgPRB2X+prfFf69UMiTBXkkacNajzBdPyD+Hb6b3ix5vdNY2IF/04wi6vnGDxRJQMovVzpA9B3Pv/Q4TuAHnuqWfvlEjEArBpVtDDkliNh7n8sJurdZMZD5i3aiyqotY1FAtvVnceY7+mD+tL9lOiGr8N5mZxqLaStg7jc3z2rjDDN5YNGKoFluyLra+O+JKKDneqTLJJKJFYNuhJJsAf3aqNgb787jG3U32WTdZaGkdz6oR3H+XOLDPkhlYyfneWTckUAzULCjsK4v88A9WCZXSxXxHc0zk0fy22HthZHgQw3MSZNqP916H1b19u2IM4Jlcuo1IAAU7nmyPcenfFp6zkFVPGXZkF/zL3U/69sLviCcQQrIq2W7E8f64mzHcrvUs0AtsxVTTLRIY+1dsR5ObNOdRdo07L3I97w6+H/h1JguYlYs7bixsvsBeGz9OX1whiL7ZKPxfqB/pjI6jKP3T+WGzdPXGh6euCxVxPJnJTdED6DAjrIeWOLD9iHrjY5Bd8Q4pmilYrDQHlj+pwP8AZ9dfhU9zsW+noMWHK5NZI2nfcIxATtt3J7n8sM8t0hGkmvcqF0ki6BW6r674FETmVOKCjpKaRdKex9vbBUXTnO+mj6c7e/vi29O6UrSyQSHWoVGsgX5wbG3bbbD6DpaDD2hvsc8g6UTZC0e+2+DI+iN6Y6A3R03YGio9OR6HBkHT0IB9cNQE6pzKf4YZhSovFWfw+4xL/ZTE+HENbj5j2X+Y+vtzi95yLVKkCHRrFs43NXRC+hN874cdP6PFCgVFoXX/AKk9zitmSfUOf5H4S07nzMfmY9/9B7YOX4ZrcD6j1/8AXF/XKLgLrE/gxM4UEgDn1Jq8NQJdRlOm6ekYHlLOTSoB5ifp2+vGIp+iiFDmc0t0RSKNQjBPp+I+p/TF3yHR1juUsXlK2XI7fugdhg6SBHQalBBo0fUbjFKK+wnNlC6j8LpmVjnikjoKaLqGjZG5sWKN9+2IOlfCx0SJmDIkSAaXchWK0QykqTqT0s98XpOhxq7sP7uXV4kJAKFv3gOxPetjgTIj7UzM+yRSBVjHFjhmPevTjA0TuEUUcIUBIn0gDT923Hb+mPYMzXxZIjsojWlYqOeAax7Fej7IXqe7P//Z"
                )
            )
            i++
        }


    }

    override suspend fun deleteAll() {
        dataBase.getBetDao().deleteAll()
    }
}


