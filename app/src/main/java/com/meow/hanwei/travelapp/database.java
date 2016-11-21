package com.meow.hanwei.travelapp;

import java.util.ArrayList;

/**
 * Created by HanWei on 19/11/2016.
 */
public class database {
    private String[] parks = {
            "Bukit Timah Nature Reserve",
            "Chinese and Japanese Gardens",
            "East Coast Park Singapore",
            "Fort Canning Park",
            "Gardens by the Bay",
            "Jurong Bird Park",
            "Marina Barrage Singapore",
            "National Orchid Garden",
            "Singapore Botanic Gardens",
            "Sungei Buloh Nature Park",
            "Treetop Walk at MacRitchie Reservoir"
    };

    private String[] religious = {
            "Al - Abrar Mosque",
            "Buddha Tooth Relic Temple",
//            "Cathedral of the Good Shepherd Singapore",
//            "Chinese Methodist Church Singapore",
//            "Church of St Gregory the Illuminator Singapore",
            "Hajjah Fatimah Mosque Beach Road",
            "Haw Par Villa",
            "Sri Krishnan Temple Waterloo Street",
            "Sri Srinivasa Perumal Temple",
            "Sri Thendayuthapani Temple Tank Road",
            "Masjid Sultan"
//            "Thian Hock Keng Temple"
    };

    private String[] museums = {
            "ArtScience Museum",
            "Asian Civilisations Museum",
            "Chinatown Heritage Centre",
            "Malay Heritage Centre",
            "National Museum of Singapore",
            "Peranakan Museum",
            "Red Dot Design Museum",
            "Singapore Art Museum",
            "Singapore Mint Coin Gallery",
            "Singapore Navy Museum",
            "Singapore Philatelic Museum"
    };

    private String[] entertainment = {
            "Esplanade Theatres on the Bay",
            "Geylang Serai Market Singapore",
            "G Max Reverse Bungy Singpore",
            "Marina Bay Sands Casino Singapore",
            "Resort World Sentosa Casino Singapore",
            "Sentosa Skyline Luge and Skyride",
            "Singapore Bazaar and Flea Market",
            "Singapore Discovery Centre",
            "Singapore F1 Grand Prix",
            "Singapore Flyer",
            "Science Centre Singapore",
            "Singapore Night Safari",
            "Singapore Zoo",
            "Underwater World",
            "Universal Studios Singapore"
    };

    private String[] food = {
            "Lau Pa Sat(Telok Ayer Market)",
            "Maxwell Road Hawker Centre"
    };

    private String[] parkInfo = {
            "This protected rainforest, just 12km away from the city's high-rises, offers hiking and bike trails for observing some of the rarest insect, bird and mammal species in the world.",
            "The stunning colors of the plants and rock formations in the Chinese Garden contrast with the thought-provoking Japanese Garden.",
            "The city’s most popular stretch of beach always buzzes with activity – even when it’s not playing host to the wide spectrum of sporting events that grace its shores regularly, like the Singapore Marathon and Xtreme Championship.",
            "Fort Canning Park is one of Singapore's most historic landmarks. Visit the park today and experience the tranquillity once enjoyed by the Malay royals of yore.",
            "An integral part of Singapore's \"City in a Garden\" vision, Gardens by the Bay spans a total of 101 hectares of prime land at the heart of Singapore's new downtown - Marina Bay. Comprising three waterfront gardens - Bay South, Bay East and Bay Central - Gardens by the Bay will be a showcase of horticulture and garden artistry that will bring the world of plants to Singapore and present Singapore to the World.",
            "Fly in to Asia's largest bird paradise and feast your senses on over 5000 colourful birds across 400 species. Spanning over 20.2 Hectares of naturalistic habitats and giant walk-in aviaries, enjoy close interactions with the feathered residents at daily feeding sessions and be tickled by their show-stopping antics in exciting bird shows.",
            "Be captivated by the bright lights of the Singapore Flyer and the Central Business District skyline against the sky at sunset, reflected in the still waters of the Marina Barrage. This Reservoir in the City was created with three key benefits, to provide water supply, flood control and a lifestyle attraction.",
            "The National Orchid Garden is a special part of the Singapore Botanic Gardens and not to be missed. Every display is perfect and a marvel to observe.",
            "This national park is open daily and features beautiful lakes, animals, flowers and plants, including one of the region's first rubber tree orchards.",
            "This 200-acre reserve, situated north of the island, is home to over 150 species of rare and exotic birds.",
            "Trails surround the park, which has an upgraded visitor’s center featuring a café, restrooms and information station. The best views are from the TreeTop Walk, a 250-meter suspension bridge across the rainforest canopy, which can be accessed along the seven-kilometer Venus Loop."
    };

    private String[] religiousInfo = {
            "A small mosque of unique design at Tanjong Pagar frequently used by Muslim professionals working in the area.",
            "The Buddha Tooth Relic Temple and Museum (BTRTM) was founded in 2002 by Venerable Shi Fazhao. It was registered by the Registrar of Societies in 20th February 2003, and as a charity under the Charities Act in 8th January 2004. The Temple is dedicated to the Maitreya Buddha, which means 'The Compassionate One', and also called 'The Future Buddha'.",
//            "Gazetted as a national monument on 1973, you’ll stand in awe at its majestic facade, with extended pinnacles and a glossy white exterior.",
//            "Telok Ayer Chinese Methodist Church is a church located in the Asian nation-city of Singapore belonging to the Chinese Annual Conference of the Methodist Church in Singapore.",
//            "The Armenian Church of Saint Gregory the Illuminator, often known as Armenian Church, is the oldest Christian church in Singapore, located at Hill Street in the Museum Planning Area, within the Central Area.",
            "Masjid Hajjah Fatimah is a mosque located along Beach Road in the Kampong Glam district within the Kallang Planning Area in Singapore. The mosque was completed in 1846.",
            "Haw Par Villa is a theme park located along Pasir Panjang Road, Singapore. The park contains over 1,000 statues and 150 giant dioramas depicting scenes from Chinese mythology, folklore, legends, history, and illustrations of various aspects of Confucianism.",
            "Sri Krishnan Temple is a temple adjacent to the Kwan Im Thong Hood Cho Temple. It is located on Waterloo Street, Singapore.",
            "Sri Srinivasa Perumal Temple or Sri Perumal Temple is one of the oldest temples in Singapore. It is located in Little India on Serangoon Road, where its tall Gopuram shows the different incarnations of Lord Vishnu.",
            "The Sri Thendayuthapani Temple, better known as the Chettiars' Temple or the Tank Road temple, is one of the Singapore Hindu community's most important monuments. It was gazetted as a national monument on 21 October 2014.",
            "Masjid Sultan, or Sultan Mosque, is a mosque located at Muscat Street and North Bridge Road within the Kampong Glam precinct of the district of Rochor in Singapore. The mosque is considered one of the most important mosques in Singapore"
//            "Thian Hock Keng Temple is the oldest and most important Hokkien or Hoklo temple in Singapore. The main temple is dedicated to Ma Cho Po (Mazu), the Taoist goddess of the sea and protector of all seamen, while a second temple at the back is a Buddhist one dedicated to Kuan Yin, the bodhisattva of mercy."
    };

    private String[] museumInfo = {
            "ArtScience Museum is a museum located within the integrated resort of Marina Bay Sands in the Downtown Core of the Central Area in Singapore.",
            "The Asian Civilisations Museum is an institution which forms a part of the four museums in Singapore, the other three being the Peranakan Museum at Old Tao Nan School, the National Museum of Singapore and the Singapore Art Museum.",
            "The Chinatown Heritage Centre is the gateway for all visitors to trace the footsteps of Singapore's early pioneers and discover the personal stories of people who made Chinatown their home.",
            "The Malay Heritage Centre is a cultural centre and museum in Singapore that showcases the culture, heritage and history of Malay Singaporeans. Located at Sultan Gate in Kampong Glam, the 8,000 square metres centre was launched on 27 November 2004.",
            "The National Museum of Singapore is the oldest museum in Singapore. Its history dates back to 1849, when it was started as a section of a library at Singapore Institution and called the Raffles Library and Museum.",
            "The Peranakan Museum is a museum in Singapore specialising in Peranakan culture. A sister museum to the Asian Civilisations Museum, it is the first of its kind in the world, that explores Peranakan cultures in Singapore and other former Straits Settlements in Malacca and Penang.",
            "A unique Singapore attraction, the Red Dot Design Museum presents the latest trends in the international design scene with a collection of more than 1,000 exhibits in the field of product design and communication design from over 50 countries.",
            "The National Gallery Singapore is an art gallery located in the Downtown Core of Singapore. Opened on 24 November 2015, it oversees the world’s largest public collection of Singapore and Southeast Asian art, consisting of over 8,000 artworks.",
            "Official mint for Singapore. The Mint has, since its establishment, undertaken most of the minting of Singapore's circulation coins.",
            "Take a trip down memory lane, as you view the guns and other components taken from our early warships. Find out more about what makes the Republic of Singapore Navy a modern and integrated maritime fighting force – the naval platforms it possesses and the arsenal that they pack.",
            "The Singapore Philatelic Museum is a museum about the postal history of Singapore and its stamps. The museum, located at 23-B Coleman Street in Singapore, was formerly part of the Anglo-Chinese School, completed in 1906"
    };

    private String[] entertainmentInfo = {
            "Esplanade – Theatres on the Bay, also known as the Esplanade Theatre or simply The Esplanade, is a 60,000 square metres performing arts centre located in Marina Bay near the mouth of the Singapore River.",
            "Geylang Serai Market is one of the biggest and busiest wet markets in Singapore. Since 1964, this market has been a focal point for the local Malay community.",
            "Singapore’s first and only reverse bungy, G-MAX has attracted hundreds of people to watch and ride on it every night. Designed and developed in New Zealand, it has been recognised as a “must-see and must-do” for braver souls in Singapore",
            "Marina Bay Sands is Asia’s most spectacular entertainment destination, located at the heart of Singapore’s CBD, with over 2,500 hotel rooms & suites, casino, restaurants, entertainment, meeting & exhibition facilities, theaters, luxurious shopping and the one & only Sands SkyPark, new experiences await you at every turn.",
            "Owned and operated by Asia's largest gaming operator, Resorts World Sentosa is unrivalled in its intimate knowledge of Asian gaming preferences. The place brims with activity 24/7 and is complemented by unparalleled customer privileges.",
            "Skyline Luge Sentosa is a luge located in Sentosa, Singapore. The attraction opened in the second half of 2005. The luge, situated on the Imbiah Lookout cluster opposite the Tiger Sky Tower, has two tracks.",
            "These folks have a weekly bazaar at Sentosa boardwalk and regularly hold bazaars at other spots around Singapore like the Marina Waterfront, Clarke Quay and One KM mall in Katong.",
            "The Singapore Discovery Centre is a 'edutainment' and tourist attraction located in Jurong West, Singapore. The centre includes exhibits which displays the history of Singapore as well as an insight on the future",
            "Enjoy the Formula 1 racing experience at the 2016 Formula 1 Singapore Airlines Singapore Grand Prix. See Formula One drivers & F1 racing teams in action at the Singapore GP",
            "Experience breathtaking, panoramic views on Asia’s largest observation wheel. For locals and tourists alike, there is only one place in Singapore that offers a bird’s eye view of the entire island city – Singapore Flyer.",
            " Singapore Science Centre is a scientific institution in Jurong East, Singapore, specialising in the promotion of scientific and technological education for the general public.",
            "The Night Safari is the world's first nocturnal zoo and is one of the most popular tourist attractions in Singapore.",
            "Singapore’s zoo is a beautiful, award-winning wildlife park, where animals can roam freely in their natural habitats. Drop in on some of our top nature and wildlife attractions – you might even make new friends!",
            "Underwater World, also known as Underwater World Singapore Pte Ltd, was an oceanarium located on the offshore Singaporean island of Sentosa. Opened in 1991, it had more than 2,500 marine animals of 250 species from different regions of the world.",
            "Singapore’s only movie-theme park features 24 rides and attractions, including a pair of dueling coasters that brush past one another in several near misses in their aerial combat."
    };

    private String[] foodInfo = {
            "Lau Pa Sat is a historic building located within the Downtown Core in the financial district of Singapore. A quaint building with ornate cast iron pillars that hold up a terracotta roof. On the roof is a distinctive clock tower. It is currently a bustling food center which offers a variety of Asian cuisine and at reasonable prices",
            "Maxwell Road Hawker Centre's reputation as a place for delicious foods is spread all over Singapore. This food court has been featured on many television channels for offering excellent authentic local food at reasonable prices."
    };

    ArrayList<String> temp = new ArrayList<>();
    public ArrayList<String> getParks() {
        temp.clear();
        for (String park :
             parks) {
            temp.add(park);
        }
        System.out.println(temp.size());
        return temp;
    }

    public ArrayList<String> getReligious() {
        temp.clear();
        for (String rel :
                religious) {
            temp.add(rel);
        }
        System.out.println(temp.size());
        return temp;
    }

    public ArrayList<String> getMuseums() {
        temp.clear();
        for (String museum :
                museums) {
            temp.add(museum);
        }
        System.out.println(temp.size());
        return temp;
    }

    public ArrayList<String> getEntertainment() {
        temp.clear();
        for (String ent :
                entertainment) {
            temp.add(ent);
        }
        System.out.println(temp.size());
        return temp;
    }

    public ArrayList<String> getFood() {
        temp.clear();
        for (String foods :
                food) {
            temp.add(foods);
        }
        System.out.println(temp.size());
        return temp;
    }

    public ArrayList<String> getParkInfo() {
        temp.clear();
        for (String info :
                parkInfo) {
            temp.add(info);
        }
        return temp;
    }

    public ArrayList<String> getReligiousInfo() {
        temp.clear();
        for (String info :
                religiousInfo) {
            temp.add(info);
        }
        return temp;
    }

    public ArrayList<String> getMuseumInfo() {
        temp.clear();
        for (String info :
                museumInfo) {
            temp.add(info);
        }
        return temp;
    }

    public ArrayList<String> getEntertainmentInfo() {
        temp.clear();
        for (String info :
                entertainmentInfo) {
            temp.add(info);
        }
        return temp;
    }

    public ArrayList<String> getFoodInfo() {
        temp.clear();
        for (String info :
                foodInfo) {
            temp.add(info);
        }
        return temp;
    }
}
