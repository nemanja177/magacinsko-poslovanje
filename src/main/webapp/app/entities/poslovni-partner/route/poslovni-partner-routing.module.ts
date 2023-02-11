import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PoslovniPartnerComponent } from '../list/poslovni-partner.component';
import { PoslovniPartnerDetailComponent } from '../detail/poslovni-partner-detail.component';
import { PoslovniPartnerUpdateComponent } from '../update/poslovni-partner-update.component';
import { PoslovniPartnerRoutingResolveService } from './poslovni-partner-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const poslovniPartnerRoute: Routes = [
  {
    path: '',
    component: PoslovniPartnerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PoslovniPartnerDetailComponent,
    resolve: {
      poslovniPartner: PoslovniPartnerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PoslovniPartnerUpdateComponent,
    resolve: {
      poslovniPartner: PoslovniPartnerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PoslovniPartnerUpdateComponent,
    resolve: {
      poslovniPartner: PoslovniPartnerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(poslovniPartnerRoute)],
  exports: [RouterModule],
})
export class PoslovniPartnerRoutingModule {}
