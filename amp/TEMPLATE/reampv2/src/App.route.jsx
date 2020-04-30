import React, { Suspense, lazy, Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import './App.css';

const SSCDashboardApp = lazy(() => import('./modules/sscdashboard'));
const AMPOfflineDownloadApp = lazy(() => import('./modules/ampoffline/Download'));

class AppRoute extends Component {
    render() {
        return (
            <Router>
                <Suspense fallback={<div>Loading...</div>}>
                    <Switch>
                        <Route path="/ssc" component={SSCDashboardApp}/>
                        <Route path="/ampofflinedownload" component={AMPOfflineDownloadApp}/>
                    </Switch>
                </Suspense>
            </Router>

        );
    }
}

export default AppRoute;
