import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnalitickaMagacinskaKarticaComponent } from '../list/analiticka-magacinska-kartica.component';
import { AnalitickaMagacinskaKarticaDetailComponent } from '../detail/analiticka-magacinska-kartica-detail.component';
import { AnalitickaMagacinskaKarticaUpdateComponent } from '../update/analiticka-magacinska-kartica-update.component';
import { AnalitickaMagacinskaKarticaRoutingResolveService } from './analiticka-magacinska-kartica-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const analitickaMagacinskaKarticaRoute: Routes = [
  {
    path: '',
    component: AnalitickaMagacinskaKarticaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnalitickaMagacinskaKarticaDetailComponent,
    resolve: {
      analitickaMagacinskaKartica: AnalitickaMagacinskaKarticaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnalitickaMagacinskaKarticaUpdateComponent,
    resolve: {
      analitickaMagacinskaKartica: AnalitickaMagacinskaKarticaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnalitickaMagacinskaKarticaUpdateComponent,
    resolve: {
      analitickaMagacinskaKartica: AnalitickaMagacinskaKarticaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(analitickaMagacinskaKarticaRoute)],
  exports: [RouterModule],
})
export class AnalitickaMagacinskaKarticaRoutingModule {}
